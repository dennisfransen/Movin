package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference companyRef = firebaseFirestore.collection("company");

    private CompanyAdapter companyAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Query query = companyRef.orderBy("company_name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<CompanyModel> options = new FirestoreRecyclerOptions.Builder<CompanyModel>()
                .setQuery(query, CompanyModel.class)
                .build();

        companyAdapter = new CompanyAdapter(options, getActivity().getSupportFragmentManager());

        RecyclerView recyclerView = view.findViewById(R.id.company_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(companyAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        companyAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        companyAdapter.stopListening();
    }
}
