package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
    }

    private EditText mReviewName, mReviewText;
    private RatingBar mStarRating;
    private FloatingActionButton mAddReview;

    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();



        mReviewName = view.findViewById(R.id.review_username_et);
        mReviewText = view.findViewById(R.id.review_text_et);
        mStarRating = view.findViewById(R.id.review_star_rating_rb);

        mAddReview = view.findViewById(R.id.review_add_review_fab);
        mAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String review_name = mReviewName.getText().toString();
                String review_text = mReviewText.getText().toString();
                float review_star_rating = mStarRating.getRating();

                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("review_name", review_name);
                reviewMap.put("review_text", review_text);
                reviewMap.put("review_star_rating", review_star_rating);

                final String companyName = getArguments().getString("COMPANY_NAME");

                firebaseFirestore.collection("company").document(companyName).
                        collection("review").document().set(reviewMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Added review to company", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(getActivity(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference doc = firebaseFirestore.collection("company").document(companyName);

                addRating(doc, review_star_rating);


            }
        });

        return view;
    }

    private Task<Void> addRating(final DocumentReference reviewDocumentReference, final float rating) {
        // Create reference for new rating, for use inside the transaction

        final DocumentReference ratingRef = reviewDocumentReference.collection("review").document();

        // In a transaction, add the new rating and update the aggregate totals
        return firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {


                CompanyModel companyModel = transaction.get(reviewDocumentReference).toObject(CompanyModel.class);

                // Compute new number of ratings
                int newNumRatings = companyModel.review_number_of_reviews + 1;

                // Compute new average rating
                double oldRatingTotal = companyModel.review_star_rating * companyModel.review_number_of_reviews;
                double newAvgRating = (oldRatingTotal + rating) / newNumRatings;

                // Set new reviewModel info
                companyModel.review_number_of_reviews = newNumRatings;
                companyModel.review_star_rating = (float) newAvgRating;

                // Update reviewModel
                transaction.set(reviewDocumentReference, companyModel);

                // Update rating
                Map<String, Object> data = new HashMap<>();
                data.put("review_star_rating", rating);
                transaction.set(ratingRef, data, SetOptions.merge());

                return null;
            }
        });
    }
}