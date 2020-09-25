package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String posterPath;
    String backdropPath;
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException{
        posterPath=jsonObject.getString("poster_path");
        backdropPath=jsonObject.getString("backdrop_path");
        title=jsonObject.getString("title");
        overview=jsonObject.getString("overview");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        //Log.d("Movie", "Movie func");
        List<Movie> moviesList = new ArrayList<>();
        for (int i=0; i<movieJsonArray.length(); i++){
            moviesList.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        Log.d("Movie", "Movie Class: "+ moviesList.toString());
        return moviesList;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
