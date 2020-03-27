package ly.bsagar.popularmovies.POJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtili {

     private static MovieClass MakeMovie(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.getString("id");
        String title = jsonObject.getString("title");
        String overview = jsonObject.getString("overview");
        String releaseDate = jsonObject.getString("release_date");
        String imagePath = jsonObject.getString("poster_path");
        String backDrop = jsonObject.getString("backdrop_path");
        String votingAvg = jsonObject.getString("vote_average");

        return new MovieClass(id,title,overview,releaseDate,imagePath,backDrop,votingAvg);
    }

    public static ArrayList<MovieClass> parseJsonMovie(JSONArray jsonArray) throws JSONException {
        int len = jsonArray.length();
        ArrayList<MovieClass> movies = new ArrayList<>();
        for (int i=0 ; i<len;i++){
            movies.add(MakeMovie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    private static TrailerClass makeTrailer(JSONObject jsonObject) throws JSONException {
        return new TrailerClass(jsonObject.getString("key"),jsonObject.getString("name"));
    }

    public static ArrayList<TrailerClass> parseJsonTrailer(JSONArray jsonArray) throws JSONException {
        int len = jsonArray.length();
        ArrayList<TrailerClass> trailers = new ArrayList<>();
        for (int i=0 ; i<len;i++){
            trailers.add(makeTrailer(jsonArray.getJSONObject(i)));
        }
        return trailers;
    }

    public static ArrayList<ReviewClass> parseJsonReview(JSONArray jsonArray)  throws JSONException {
        int len = jsonArray.length();
        ArrayList<ReviewClass> reviews = new ArrayList<>();
        for (int i=0 ; i<len;i++){
            reviews.add(new ReviewClass(jsonArray.getJSONObject(i).getString("author"),jsonArray.getJSONObject(i).getString("content")));
        }
        return reviews;
    }

}
