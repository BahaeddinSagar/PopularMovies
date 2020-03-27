package ly.bsagar.popularmovies.POJO;

public class ReviewClass {
    String title;
    String body;

    public ReviewClass(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
