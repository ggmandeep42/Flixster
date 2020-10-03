package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyDlo81qov_MAYGyC3a3TOOtdfJI5iOyJeI";
    public static final String Videos_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView playerView;
    TextView tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        playerView = findViewById(R.id.player);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);

        final Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText("Summary: "+movie.getOverview());
        ratingBar.setRating(movie.getRating().floatValue());
        tvReleaseDate.setText("Release Date: "+movie.getReleaseDate());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(Videos_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray jsonArrayResult = json.jsonObject.getJSONArray("results");
                    if (jsonArrayResult.length() == 0){
                        return;
                    }
                    String youTubeKey = jsonArrayResult.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youTubeKey);
                    initializeYoutube(youTubeKey, movie.getRating());
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("DetailActivity", "onFailure");
            }
        });

    }

    private void initializeYoutube(final String youTubeKey, final Double rating) {
        playerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");
                if (rating > 5){
                    youTubePlayer.loadVideo(youTubeKey);
                }
                else {
                    youTubePlayer.cueVideo(youTubeKey);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    }
}