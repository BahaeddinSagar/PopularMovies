package ly.bsagar.popularmovies.Utils;

public class MovieClass {
    private String title;
    private String overview;
    private String releaseDate;
    private String imagePath;
    private String backDrop;
    private String vote_average;


    public MovieClass(String title, String overview, String releaseDate, String imagePath, String backDrop, String vote_average) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.imagePath = "https://image.tmdb.org/t/p/w185/"+imagePath;
        this.backDrop = "https://image.tmdb.org/t/p/w342/"+backDrop;
        this.vote_average = vote_average;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBackDrop() {
        return backDrop;
    }
}
