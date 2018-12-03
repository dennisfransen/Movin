package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.LatLng;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class AddCompanyFragment extends Fragment {


    public AddCompanyFragment() {
    }

    SearchFragment searchFragment;

    static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 3;

    private EditText mCompanyName, mCompanyOrgNumber, mCompanyAddress, mCompanyPhoneNumber, mCompanyEmail, mCompanyWebsite;
    private Button mGoogle, mFinish;
    private CheckBox mCompanyCleaning, mCompanyMoving;

    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_company, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        searchFragment = new SearchFragment();


        mCompanyAddress = view.findViewById(R.id.add_company_address_et);
        mCompanyWebsite = view.findViewById(R.id.add_company_website_et);
        mCompanyOrgNumber = view.findViewById(R.id.add_company_org_number_et);
        mCompanyPhoneNumber = view.findViewById(R.id.add_company_phone_number_et);
        mCompanyEmail = view.findViewById(R.id.add_company_email_et);
        mCompanyCleaning = view.findViewById(R.id.add_company_clean_cb);
        mCompanyMoving = view.findViewById(R.id.add_company_move_cb);


        mCompanyName = view.findViewById(R.id.add_company_name_et);
        mCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGooglePlaces();
            }
        });

        mGoogle = view.findViewById(R.id.add_google_btn);
        mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGooglePlaces();
            }
        });

        mFinish = view.findViewById(R.id.add_finish_btn);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mCompanyOrgNumber.length() < 10) {
                    mCompanyOrgNumber.setHint("Missing organization number...");
                    Toast.makeText(getActivity(), "Organizations number is either missing, to short or empty", Toast.LENGTH_SHORT).show();

                }
                else if (mCompanyPhoneNumber.length() < 10) {
                    mCompanyPhoneNumber.setHint("Missing phone number...");
                    Toast.makeText(getActivity(), "Phone number is either missing, to short or empty", Toast.LENGTH_SHORT).show();

                }
                else if (!isEmailValid(mCompanyEmail.getText().toString())) {
                    mCompanyEmail.setHint("Missing email address...");
                    Toast.makeText(getActivity(), "Email is either missing, miss spelled or empty", Toast.LENGTH_SHORT).show();

                } else {

                    String companyName = mCompanyName.getText().toString();
                    String companyAddress = mCompanyAddress.getText().toString();
                    String companyWebsite = mCompanyWebsite.getText().toString();
                    String companyOrgNumber = mCompanyOrgNumber.getText().toString();
                    String companyPhoneNumber = mCompanyPhoneNumber.getText().toString();
                    String companyEmail = mCompanyEmail.getText().toString();
                    boolean companyMoving = mCompanyMoving.isChecked();
                    boolean companyCleaning = mCompanyCleaning.isChecked();

                    HashMap<String, Object> addCompanyMap = new HashMap<>();
                    addCompanyMap.put("company_name", companyName);
                    addCompanyMap.put("company_address", companyAddress);
                    addCompanyMap.put("company_website", companyWebsite);
                    addCompanyMap.put("company_org_number", companyOrgNumber);
                    addCompanyMap.put("company_contact_number", companyPhoneNumber);
                    addCompanyMap.put("company_contact_email", companyEmail);
                    addCompanyMap.put("company_moving_type", companyMoving);
                    addCompanyMap.put("company_cleaning_type", companyCleaning);

                    firebaseFirestore.collection("company").document(companyName).set(addCompanyMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Awesome! You added a company", Toast.LENGTH_SHORT).show();
//                            setFragment(searchFragment);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getActivity(), data);

            if (place.getPlaceTypes().contains(65)) {
                mCompanyName.setText(place.getName());
                mCompanyAddress.setText(place.getAddress());
                mCompanyPhoneNumber.setText(place.getPhoneNumber());
                mCompanyWebsite.setText(place.getWebsiteUri().toString());


//                com.google.android.gms.maps.model.LatLng location = place.getLatLng();
//                double latitute = location.latitude;
//                double longitude = location.longitude;
//
//                Bundle longLatData = new Bundle();
//                longLatData.putDouble("LATITUDE", latitute);
//                longLatData.putDouble("LONGITUDE", longitude);
//                setArguments(longLatData, companyPageFragment);

                // TODO: Check if i can get data from lat and long. Add to "location FAB" in company page fragment.

            } else {
                Toast.makeText(getActivity(), "Please select a company", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void openGooglePlaces() {
        try {
            AutocompleteFilter filter = new AutocompleteFilter.Builder().setCountry("SE").build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(filter)
                    .build(getActivity());

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
