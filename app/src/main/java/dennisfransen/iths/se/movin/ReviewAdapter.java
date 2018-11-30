package dennisfransen.iths.se.movin;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ReviewAdapter extends FirestoreRecyclerAdapter<ReviewModel, ReviewAdapter.ReviewHolder> {

    private FragmentManager mContext;

    public ReviewAdapter(@NonNull FirestoreRecyclerOptions<ReviewModel> options, FragmentManager context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull ReviewModel model) {

        holder.reviewName.setText(model.getReview_name());
        holder.reviewText.setText(model.getReview_text());
//        holder.starRating.setNumStars(model.getReview_star_rating().getNumStars());


    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_review, viewGroup, false);
        return new ReviewHolder(view, mContext);
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        private TextView reviewName, reviewText;
//        private RatingBar starRating;

        public ReviewHolder(@NonNull View itemView, FragmentManager context) {
            super(itemView);
            mContext = context;

            reviewName = itemView.findViewById(R.id.review_name_tv);
            reviewText = itemView.findViewById(R.id.review_text_tv);
//            starRating = itemView.findViewById(R.id.review_star_rating);

        }
    }
}


