package com.example.finnkino.utils;

import static com.example.finnkino.utils.NetworkUtils.generateStringURL;
import static com.example.finnkino.utils.NetworkUtils.getJSONFromURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class OmdbApi {
    private static final String BASE_URI = "http://www.omdbapi.com/";
    private static final String API_KEY = "bf3c659f";
    private static final String IMDB_RATING_KEY = "imdbRating";

    public static String getRatingFromOmdbApi (String title, String year) {
        String imdbRating = null;
        String uriString = generateStringURL(BASE_URI, "apikey", API_KEY, "t", title, "y", year);
        try {
            JSONObject jsonResponse = getJSONFromURL(uriString);
            imdbRating = jsonResponse.getString(IMDB_RATING_KEY);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return imdbRating;
    }
}
