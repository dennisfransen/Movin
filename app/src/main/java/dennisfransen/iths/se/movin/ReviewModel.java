package dennisfransen.iths.se.movin;

import android.widget.RatingBar;

public class ReviewModel {

    private String review_name, review_text;
    private RatingBar review_star_rating;

    public ReviewModel() {
    }

    public ReviewModel(String review_name, String review_text, RatingBar review_star_rating) {
        this.review_name = review_name;
        this.review_text = review_text;
        this.review_star_rating = review_star_rating;
    }

    public String getReview_name() {
        return review_name;
    }

    public String getReview_text() {
        return review_text;
    }

    public RatingBar getReview_star_rating() {
        return review_star_rating;
    }
}
