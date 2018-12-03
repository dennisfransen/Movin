package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CompanyPageFragment extends Fragment {

    public CompanyPageFragment() {
    }

    private TextView mCompanyName, mCompanyOrgNumber, mCompanyEmail, mCompanyPhoneNumber, mCompanyAddress;
    private CheckBox mCompanyMove, mCompanyClean;
    private RatingBar mCompanyStarRating;
    private FloatingActionButton mCompanyCall, mCompanyAddReview, mCompanyWebsite, mCompanyLocation; // Send user to location on googlemaps (Intent)

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reviewRef;

    private String companyWebsite;

    private ReviewAdapter reviewAdapter;

    Place place;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_page, container, false);

        final String companyName = getArguments().getString("COMPANY_NAME");

        mCompanyName = view.findViewById(R.id.company_profile_name_tv);
        mCompanyOrgNumber = view.findViewById(R.id.company_profile_org_number_tv);
        mCompanyEmail = view.findViewById(R.id.company_profile_email_tv);
        mCompanyPhoneNumber = view.findViewById(R.id.company_profile_phone_number_tv);
        mCompanyAddress = view.findViewById(R.id.company_profile_address_tv);


        mCompanyMove = view.findViewById(R.id.company_profile_move_cb);
        mCompanyClean = view.findViewById(R.id.company_profile_clean_cb);

        mCompanyStarRating = view.findViewById(R.id.company_profile_star_rating_rb);

        mCompanyLocation = view.findViewById(R.id.company_profile_location_fab);
        mCompanyWebsite = view.findViewById(R.id.company_profile_website_fab);
        mCompanyCall = view.findViewById(R.id.company_profile_phone_fab);
        mCompanyAddReview = view.findViewById(R.id.company_profile_add_review_fab);

        firebaseFirestore.collection("company").document(companyName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String companyName = documentSnapshot.getString("company_name");
                String companyOrgNumber = documentSnapshot.getString("company_org_number");
                String companyEmail = documentSnapshot.getString("company_contact_email");
                String companyPhoneNumber = documentSnapshot.getString("company_contact_number");
                String companyAddress = documentSnapshot.getString("company_address");
                companyWebsite = documentSnapshot.getString("company_website");

                mCompanyName.append(companyName);
                mCompanyOrgNumber.append(companyOrgNumber);
                mCompanyEmail.append(companyEmail);
                mCompanyPhoneNumber.append(companyPhoneNumber);
                mCompanyAddress.append(companyAddress);

                if (documentSnapshot.getBoolean("company_moving_type").equals(true))
                    mCompanyMove.setChecked(true);

                if (documentSnapshot.getBoolean("company_cleaning_type").equals(true))
                    mCompanyClean.setChecked(true);

            }
        });

        mCompanyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"));

            }
        });

        mCompanyWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(companyWebsite));
                startActivity(intent);
            }
        });

        mCompanyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = mCompanyPhoneNumber.getText().toString();
                startCallFunction(number);

            }
        });

        mCompanyAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReviewFragment reviewFragment = new ReviewFragment();

                String companyName = mCompanyName.getText().toString();

                // Remove "Company:" from Text View that is in fragment_company_page.xml (id: company_profile_name_tv)
                companyName = companyName.replace("Company: ", "");

                Bundle companyData = new Bundle();
                companyData.putString("COMPANY_NAME", companyName);

                reviewFragment.setArguments(companyData);

                setFragment(reviewFragment);


            }
        });

        reviewRef = firebaseFirestore.collection("company/" + companyName + "/review");

        Query query = reviewRef.orderBy("review_name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReviewModel> options = new FirestoreRecyclerOptions.Builder<ReviewModel>()
                .setQuery(query, ReviewModel.class)
                .build();

        reviewAdapter = new ReviewAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.review_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(reviewAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reviewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        reviewAdapter.stopListening();
    }

    public void startCallFunction(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

}
