package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    Integer movieId;
    String releaseDate;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double rating;

    //For Parcel library
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException{
        posterPath=jsonObject.getString("poster_path");
        backdropPath=jsonObject.getString("backdrop_path");
        title=jsonObject.getString("title");
        overview=jsonObject.getString("overview");
        rating=jsonObject.getDouble("vote_average");
        movieId=jsonObject.getInt("id");
        releaseDate=jsonObject.getString("release_date");
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

    public Double getRating() {
        return rating;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
