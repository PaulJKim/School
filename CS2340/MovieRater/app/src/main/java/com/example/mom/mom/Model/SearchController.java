package com.example.mom.mom.Model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.MovieListActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesse on 2/18/2016.
 */
public class SearchController {

    private RequestQueue m_oQueue;
    private final List<Movie> m_aMovies = new ArrayList<>();
    private final String m_szURL = "http://www.omdbapi.com/?s=";
    //private String m_szSearch;

    /**
     * Gets search results
     * @param oContext context
     */
    public void getSearchResults(Context oContext, String szSearch) {
        m_oQueue = Volley.newRequestQueue(oContext);

        //Process String
        szSearch = processSearchString(szSearch);

        StringRequest request = new StringRequest(Request.Method.GET, m_szURL + szSearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processResults(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        m_oQueue.add(request);
    }

    /**
     * Processes JSON results and adds to movies.
     * @param szResults JSON results
     */
    public void processResults(String szResults) {
        //Clear list
        m_aMovies.clear();

        try {
            //Parse from JSON
            JSONObject jObject = new JSONObject(szResults);
            JSONArray aMovieList = jObject.getJSONArray("Search");
            for (int i = 0; i < aMovieList.length(); i++) {
                //Getting Movie Info
                JSONObject oJSONMovie = aMovieList.getJSONObject(i);

                //Only add movies
                if(oJSONMovie.getString("Type").equals("movie")) {
                    String szTitle = oJSONMovie.getString("Title");
                    String szPosterURL = oJSONMovie.getString("Poster");
                    String szYear = oJSONMovie.getString("Year");
                    String szID = oJSONMovie.getString("imdbID");

                    //Adding new movie
                    Movie oMovie = new Movie(szTitle, szPosterURL, szYear, szID);
                    //Todo: Remove this!!!!
                    oMovie.rate(Session.getUser(), (float) (5 * Math.random()));
                    m_aMovies.add(oMovie);
                    if (!Session.getMovies().containsKey(szID)) {
                        Session.getMovies().put(szID, oMovie);
                    }
                }
            }

            Log.d("?", "Something happened");
            //Set notification
            MovieListActivity.m_aAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Processes search string to make it fit search protocol
     */
    private String  processSearchString(String szSearch) {
        szSearch = szSearch.trim();
        szSearch = szSearch.replace(" ", "%20");

        //Log.d("Search", szSearch);
        return szSearch;
    }

    /*public String getSearchString() {
        return m_szSearch;
    }*/

    public List<Movie> getMovies() {
        return m_aMovies;
    }

}
