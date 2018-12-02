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
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public class CompanyPageFragment extends Fragment {

    public CompanyPageFragment() {
    }

    private TextView mCompanyName, mCompanyOrgNumber, mCompanyEmail, mCompanyPhoneNumber, mCompanyAddress;
    private CheckBox mMove, mClean;
    private RatingBar mStarRating;
    private FloatingActionButton mCall, mAddReview;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reviewRef;

    private ReviewAdapter reviewAdapter;

    private static final String Tag = "FireLog";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_page, container, false);


        final String companyName = getArguments().getString("COMPANY_NAME");

        //region connect to xml
        mCompanyName = view.findViewById(R.id.company_profile_name_tv);
        mCompanyOrgNumber = view.findViewById(R.id.company_profile_org_number_tv);
        mCompanyEmail = view.findViewById(R.id.company_profile_email_tv);
        mCompanyPhoneNumber = view.findViewById(R.id.company_profile_phone_number_tv);
        mCompanyAddress = view.findViewById(R.id.company_profile_address_tv);

        mMove = view.findViewById(R.id.company_profile_move_cb);
        mClean = view.findViewById(R.id.company_profile_clean_cb);

        mStarRating = view.findViewById(R.id.company_profile_star_rating_rb);

        mCall = view.findViewById(R.id.company_profile_phone_fab);
        mAddReview = view.findViewById(R.id.company_profile_add_review_fab);
        //endregion

        firebaseFirestore.collection("company").document(companyName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String companyName = documentSnapshot.getString("company_name");
                String companyOrgNumber = documentSnapshot.getString("company_org_number");
                String companyEmail = documentSnapshot.getString("company_contact_email");
                String companyPhoneNumber = documentSnapshot.getString("company_contact_number");
                String companyAddress = documentSnapshot.getString("company_address");

                mCompanyName.append(companyName);
                mCompanyOrgNumber.append(companyOrgNumber);
                mCompanyEmail.append(companyEmail);
                mCompanyPhoneNumber.append(companyPhoneNumber);
                mCompanyAddress.append(companyAddress);

                if (documentSnapshot.getBoolean("company_moving_type").equals(true))
                    mMove.setChecked(true);

                if (documentSnapshot.getBoolean("company_cleaning_type").equals(true))
                    mClean.setChecked(true);

            }
        });


        firebaseFirestore.collection("company").document(companyName).collection("review").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//                List<Double> zero = new ArrayList<>();
//                List<Double> one = new ArrayList<>();
//                List<Double> two = new ArrayList<>();
//                List<Double> three = new ArrayList<>();
//                List<Double> four = new ArrayList<>();
//                List<Double> five = new ArrayList<>();

                List<Double> numbers = new ArrayList<>();

                double review_star_rating = 0;

                if (e != null) {
                    Log.d(Tag, "Error : " + e.getMessage());
                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        review_star_rating = doc.getDocument().getDouble("review_star_rating");

                        numbers.add(review_star_rating);

//                        if (review_star_rating == 0.0)
//                            zero.add(review_star_rating);
//                        if (review_star_rating == 1.0)
//                            one.add(review_star_rating);
//                        if (review_star_rating == 2.0)
//                            two.add(review_star_rating);
//                        if (review_star_rating == 3.0)
//                            three.add(review_star_rating);
//                        if (review_star_rating == 4.0)
//                            four.add(review_star_rating);
//                        if (review_star_rating == 5.0)
//                            five.add(review_star_rating);

                    }

                }

                double countNumber = 4;
                double countZero = 0;
                double countOne = 0;
                double countTwo = 0;
                double countThree = 0;
                double countFive = 0;

                for (int i = 0; i < countNumber; i++) {

                    countNumber++;

                    for (int j = 0; i < numbers.size(); i++) {

                        if (numbers.get(j).equals(countNumber))
                            countZero++;
                    }
                }


                Log.d(Tag, "m " + countNumber);

//                if (numbers.contains(3.0)) ;
//                {
//
//                    Log.d(Tag, "Contains: three and: " + numbers.size());
//                }

//                Log.d(Tag, "Score: " + review_star_rating);
//
//                Log.d(Tag, "Zero: " + zero);
//                Log.d(Tag, "One: " + one);
//                Log.d(Tag, "two: " + two);
//                Log.d(Tag, "three: " + three);
//                Log.d(Tag, "four: " + four);
//                Log.d(Tag, "five: " + five);
//                Log.d(Tag, "-----");
//                Log.d(Tag, "-----");
//
//                Log.d(Tag, "Size:" + zero.size());
//                Log.d(Tag, "Size: " + one.size());
//                Log.d(Tag, "Size: " + two.size());
//                Log.d(Tag, "Size: " + three.size());
//                Log.d(Tag, "Size: " + four.size());
//                Log.d(Tag, "Size: " + five.size());
//
//                double calcZero = 0;
//                double calcOne = 0;
//                double calcTwo = 0;
//                double calcThree = 0;
//                double calcFour = 0;
//                double calcFive = 0;
//
//                double number = 0.0;
//                zero.contains(number);

//                if (!zero.isEmpty())
//                    calcZero = zero.size() * zero.get(0);
//                if (!one.isEmpty())
//                    calcOne = one.size() * one.get(0);
//                if (!two.isEmpty())
//                    calcTwo = two.size() * two.get(0);
//                if (!three.isEmpty())
//                    calcThree = three.size() * three.get(0);
//                if (!four.isEmpty())
//                    calcFour = four.size() * four.get(0);
//                if (!five.isEmpty())
//                    calcFive = five.size() * five.get(0);
//
//
//                double halfTotal = calcZero + calcOne + calcTwo + calcThree + calcFour + calcFive;
//                int total = zero.size() + one.size() + two.size() + three.size() + four.size() + five.size() + 1;
//                double superTotal = halfTotal / total;

//                Log.d(Tag, "Total: " + superTotal);
            }
        });


//        firebaseFirestore.collection("company").document(companyName).collection("review").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()) {
//                    int count = 0;
//                    for (DocumentSnapshot document : task.getResult()) {
//                        count++;
//                        Log.d(Tag, "Amount of reviewers: " + count);
//                    }
//
//                } else {
//                    Log.d("Error getting documents: ", "What");
//                }
//            }
//        });


        //region tries

//        firebaseFirestore.collection("company").document(companyName).collection("review").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//
//                if (e != null) {
//                    Log.d(Tag, "Error : " + e.getMessage());
//                }
//                List<Double> awesome = new ArrayList<>();
//
//                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
//
//                    if (doc.getType() == DocumentChange.Type.ADDED) {
//
//                        awesome.add(doc.getDocument().getDouble("review_star_rating"));
//
//                        Log.d(Tag, "Vote Value : " + awesome);
//
//                    }
//
//                }
//
//                firebaseFirestore.collection("company").document("Dennis Flytt").collection("review").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        List<Integer> count = new ArrayList<>();
//                        int i = 0;
//
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                i++;
//                                count.add(i);
//                                Log.d(Tag, "Count: " + count);
//                            }
//
//                        } else {
//                            Log.d("Error getting documents: ", "What");
//                        }
//                    }
//                });
//
//            }
//        });
        //endregion

        //region buttons

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = mCompanyPhoneNumber.getText().toString();
                startCallFunction(number);

            }
        });

        mAddReview.setOnClickListener(new View.OnClickListener() {
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

        //endregion


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
