package com.example.mom.mom.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mom.mom.Model.Major;
import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.SearchController;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.VolleyImage;
import com.example.mom.mom.MovieListActivity;
import com.example.mom.mom.R;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.HomeView;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jesse on 2/29/16.
 */
public class HomePresenter implements ClickListener, OnCreate {

    public static final String EXTRA_TAG = "com.example.mom.mom.Presenter.Search";
    public static final int MAX_SUGGESTIONS = 5;

    private HomeView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;

    public HomePresenter(HomeView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {
        String szSearch = m_oView.getSearch();
        m_oNavigator.addExtra(EXTRA_TAG, szSearch);
        m_oNavigator.startNewActivity(MovieListActivity.class);
    }

    @Override
    public void onCreate() {
        ArrayList<Movie> aMovies = new ArrayList<>(Session.getMovies().values());

        Collections.sort(aMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                if (lhs.getRating() > rhs.getRating()) {
                    return -1;
                } else if (lhs.getRating() < rhs.getRating()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        List<Movie> aOverallSublist = aMovies.subList(0, Math.min(MAX_SUGGESTIONS, aMovies.size()));

        Collections.sort(aMovies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                if (lhs.getRating() > rhs.getRating()) {
                    return -1;
                } else if (lhs.getRating() < rhs.getRating()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        List<Movie> aMajorSublist = aMovies.subList(0, Math.min(MAX_SUGGESTIONS, aMovies.size()));

        Log.d("?", "");
        ListView lvOverall = m_oView.getListViewOverall();
        ListView lvMajor = m_oView.getListViewMajor();
        RecommendAdapter adapterMajor = new RecommendAdapter(m_oContext, aMajorSublist, Session.getUser().getMajor());
        RecommendAdapter adapterOverall = new RecommendAdapter(m_oContext, aOverallSublist, null);
        lvOverall.setAdapter(adapterOverall);
        lvMajor.setAdapter(adapterMajor);
    }

    public class RecommendAdapter extends ArrayAdapter<Movie> {
        Major eMajor;
        public RecommendAdapter(Context oContext, List<Movie> aMovies, Major eMajor) {
            super(oContext, 0, aMovies);
            this.eMajor = eMajor;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Movie oMovie = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home, parent, false);
            }

            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.txtTitle);
            ImageView imgPoster = (ImageView) convertView.findViewById(R.id.imgPoster);

            // Populate the data into the template view using the data object
            tvName.setText(oMovie.getTitle());

            //Populate ImageView using Volley
            ImageLoader mImageLoader = VolleyImage.getInstance(this.getContext()).getImageLoader();
            NetworkImageView mNetworkImageView = (NetworkImageView) imgPoster;
            String szURL = oMovie.getPosterURL();
            if (szURL.equals("N/A")) {

            } else {
                mNetworkImageView.setImageUrl(oMovie.getPosterURL(), mImageLoader);
            }

            //Set rating Text
            TextView oRate = (TextView) convertView.findViewById(R.id.txtRating);
            float fRating;
            if (eMajor != null) {
                fRating = oMovie.getRating(eMajor);
            } else {
                fRating = oMovie.getRating();
            }
            oRate.setText(String.format("%.2f/5", fRating));

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
