package dennisfransen.iths.se.movin;

public class ReviewModel {

    private String review_name, review_text;


    public ReviewModel() {

    }

    public ReviewModel(String review_name, String review_text) {
        this.review_name = review_name;
        this.review_text = review_text;
    }

    public String getReview_name() {
        return review_name;
    }

    public String getReview_text() {
        return review_text;
    }

}
