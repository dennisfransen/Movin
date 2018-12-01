package dennisfransen.iths.se.movin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setReviewDetails (Context context, String review_name, String review_text, int review_star_rating) {

        TextView mReviewName = mView.findViewById(R.id.review_name_tv);
        TextView mReviewText = mView.findViewById(R.id.review_text_tv);

        mReviewName.setText(review_name);
        mReviewText.setText(review_text);

    }
}
