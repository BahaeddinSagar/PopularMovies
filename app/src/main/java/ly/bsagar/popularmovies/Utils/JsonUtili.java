package ly.bsagar.popularmovies.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtili {

     private static MovieClass MakeMovie(JSONObject jsonObject) throws JSONException {

        String title = jsonObject.getString("title");
        String overview = jsonObject.getString("overview");
        String releaseDate = jsonObject.getString("release_date");
        String imagePath = jsonObject.getString("poster_path");
        String backDrop = jsonObject.getString("backdrop_path");
        String votingAvg = jsonObject.getString("vote_average");

        return new MovieClass(title,overview,releaseDate,imagePath,backDrop,votingAvg);
    }

    public static ArrayList<MovieClass> parseJson(JSONArray jsonArray) throws JSONException {
        int len = jsonArray.length();
        ArrayList<MovieClass> movies = new ArrayList<>();
        for (int i=0 ; i<len;i++){
            movies.add(MakeMovie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }
}
