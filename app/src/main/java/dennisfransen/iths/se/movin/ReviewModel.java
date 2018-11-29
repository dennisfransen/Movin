package dennisfransen.iths.se.movin;

public class ReviewModel {

    private String review_name, review_text;
    private int review_star_rating;

    public ReviewModel() {
    }

    public ReviewModel(String review_name, String review_text, int review_star_rating) {
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

    public float getReview_star_rating() {
        return review_star_rating;
    }
}
