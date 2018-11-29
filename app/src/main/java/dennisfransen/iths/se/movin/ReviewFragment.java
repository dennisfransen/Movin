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
import com.google.firebase.firestore.FirebaseFirestore;

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

        final String companyName = getArguments().getString("COMPANY_NAME");

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
            }
        });

        return view;
    }
}
