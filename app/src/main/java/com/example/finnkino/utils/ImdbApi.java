package com.example.finnkino.utils;

import static com.example.finnkino.utils.NetworkUtils.getJSONFromURL;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ImdbApi {
    private static final String BASE_URI = "https://imdb-api.com/en/API";
    private static final String SEARCH_TITLE = "SearchTitle";
    private static final String RATINGS = "Ratings";
    private static final String API_KEY = "k_j0hz00qa";
    private static final String SEARCH_MOVIE_RESULT = "results";
    private static final String MOVIE_ID = "id";
    private static final String IMDB_RATING_KEY = "imDb";

    public static String getRatingFromImdbApi (String title, String year) {
        String imdbRating = "";
        try {
            String uriSearchId = Uri.parse(BASE_URI).buildUpon().appendPath(SEARCH_TITLE).appendPath(API_KEY).appendPath(title + " " + year).build().toString();
            JSONArray jsonArraySearchId = getJSONFromURL(uriSearchId).getJSONArray(SEARCH_MOVIE_RESULT);
            String movieId = jsonArraySearchId.getJSONObject(0).getString(MOVIE_ID);
            String uriRating = Uri.parse(BASE_URI).buildUpon().appendPath(RATINGS).appendPath(API_KEY).appendPath(movieId).build().toString();
            JSONObject jsonSearchRating = getJSONFromURL(uriRating);
            imdbRating = jsonSearchRating.getString(IMDB_RATING_KEY);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return imdbRating;

    }
}
