package com.example.mom.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.VolleyImage;
import com.example.mom.mom.Presenter.HomePresenter;
import com.example.mom.mom.Presenter.MovieListPresenter;
import com.example.mom.mom.Presenter.MoviePresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.MovieView;

public class MovieActivity extends AppCompatActivity implements MovieView {
    private Movie oMovie;

    private TextView txtTitle;
    private TextView txtYear;
    private TextView txtGenres;
    private RatingBar rbRate;

    MoviePresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set stuff
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtYear = (TextView) findViewById(R.id.txtYear);
        txtGenres = (TextView) findViewById(R.id.txtGenres);
        rbRate = (RatingBar) findViewById(R.id.rbRate);

        oMovie = getMovie();

        m_oPresenter = new MoviePresenter(this, new NavigatorModel(this), this);

        //Set appbar title
        getSupportActionBar().setTitle(oMovie.getTitle());

        //Put values in
        m_oPresenter.onCreate();

        rbRate.setStepSize(0.1f);

        rbRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                m_oPresenter.onClick();
            }
        });

    }

    @Override
    public String getSearch() {
        return getIntent().getStringExtra(HomePresenter.EXTRA_TAG);
    }

    @Override
    public Movie getMovie() {
        //Grab movie
        String szID = getIntent().getStringExtra(MovieListPresenter.EXTRA_ID);
        return Session.getMovies().get(szID);
    }

    @Override
    public float getRating() {
        return rbRate.getRating();
    }

    @Override
    public void setTextRating(float fRating) {
        TextView txtRating = (TextView) findViewById(R.id.txtRating);
        txtRating.setText(String.format("%.2f", fRating));
    }

    @Override
    public void setTitle(String szTitle){
        txtTitle.setText(szTitle);
    }

    @Override
    public void setYear(String szYear){
        txtYear.setText(szYear);
    }

    @Override
    public void setImage(String szURL){
        //Populate ImageView using Volley
        ImageLoader mImageLoader = VolleyImage.getInstance(this).getImageLoader();
        NetworkImageView mNetworkImageView = (NetworkImageView) this.findViewById(R.id.imgMovPoster);
        if(!szURL.equals("N/A")) {
            mNetworkImageView.setImageUrl(oMovie.getPosterURL(), mImageLoader);
        }
    }

    @Override
    public void onBackPressed() {
        m_oPresenter.onBackPressed();
    }
}
