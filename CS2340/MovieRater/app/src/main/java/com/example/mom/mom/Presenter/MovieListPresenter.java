package com.example.mom.mom.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.SearchController;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.VolleyImage;
import com.example.mom.mom.MovieActivity;
import com.example.mom.mom.MovieListActivity;
import com.example.mom.mom.R;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.MovieListView;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;

import java.util.List;

/**
 * Created by jesse on 3/6/16.
 */
public class MovieListPresenter implements ClickListener, OnCreate {

    public static final String EXTRA_ID = "com.example.mom.mom.Presenter.ID";

    MovieListView m_oView;
    Navigator m_oNavigator;
    SearchController m_oModel;
    Context m_oContext;

    public MovieListPresenter(MovieListView oView, Navigator oNavigator, SearchController oModel, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oModel = oModel;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {

        String szID = m_oModel.getMovies().get(m_oView.getPosition()).getID();

        m_oNavigator.addExtra(EXTRA_ID, szID);
        m_oNavigator.addExtra(HomePresenter.EXTRA_TAG, m_oView.getSearch());

        m_oNavigator.startNewActivity(MovieActivity.class);
    }

    @Override
    public void onCreate() {
        m_oModel.getSearchResults(m_oView.getContext(), m_oView.getSearch());

        MovieListActivity.m_aAdapter = new MovieAdapter(m_oContext, m_oModel.getMovies());
    }



    /**
     * Adapter for a list
     */
    public class MovieAdapter extends ArrayAdapter<Movie> {
        public MovieAdapter(Context oContext, List<Movie> aMovies) {
            super(oContext, 0, aMovies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Movie oMovie = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView tvYear = (TextView) convertView.findViewById(R.id.txtYear);
            ImageView imgPoster = (ImageView) convertView.findViewById(R.id.imgPoster);

            // Populate the data into the template view using the data object
            tvName.setText(oMovie.getTitle());
            tvYear.setText("(" + oMovie.getYear() + ")");

            //Populate ImageView using Volley
            ImageLoader mImageLoader = VolleyImage.getInstance(this.getContext()).getImageLoader();
            NetworkImageView mNetworkImageView = (NetworkImageView) imgPoster;
            String szURL = oMovie.getPosterURL();
            if (szURL.equals("N/A")) {

            } else {
                mNetworkImageView.setImageUrl(oMovie.getPosterURL(), mImageLoader);
            }

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
