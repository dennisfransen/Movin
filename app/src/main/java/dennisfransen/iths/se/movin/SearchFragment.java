package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public static final String TAG = "SearchFragment";
    private GeoDataClient mGeoDataClient;
    private EditText mSearchField;
    private ListView mListView;
    private List<String> mListOfPredictions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchField = view.findViewById(R.id.search_field_et);
        mListView = view.findViewById(R.id.listView);
        mListOfPredictions = new ArrayList<>();
        //mListOfPredictions.add("HELLO WORLD");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mListOfPredictions);
        mListView.setAdapter(arrayAdapter);

        mGeoDataClient = Places.getGeoDataClient(getActivity());
        final AutocompleteFilter countryFilter = new AutocompleteFilter.Builder().setCountry("SE").build();

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGeoDataClient.getAutocompletePredictions(s.toString(), null, countryFilter).addOnSuccessListener(new OnSuccessListener<AutocompletePredictionBufferResponse>() {
                    @Override
                    public void onSuccess(AutocompletePredictionBufferResponse autocompletePredictions) {
                        mListOfPredictions.clear();
                        for (int i = 0; i < autocompletePredictions.getCount(); i++) {
                            mListOfPredictions.add(autocompletePredictions.get(i).getPrimaryText(null).toString());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

}
