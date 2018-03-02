package com.example.mom.mom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.SearchController;
import com.example.mom.mom.Presenter.HomePresenter;
import com.example.mom.mom.Presenter.MovieListPresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.MovieListView;

/**
 * Movie List
 */
public class MovieListActivity extends AppCompatActivity implements MovieListView {

    public static ArrayAdapter<Movie> m_aAdapter;
    private SearchController m_oSearch;
    private int m_nPosition;

    private MovieListPresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set List View
        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);
        m_oSearch = new SearchController();
        m_oPresenter = new MovieListPresenter(this, new NavigatorModel(this), new SearchController(), this);

        //Search movies
        m_oPresenter.onCreate();


        lvMovies.setAdapter(m_aAdapter);


        //Set onclick
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_nPosition = position;
                m_oPresenter.onClick();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent oIntent = new Intent(this, HomeActivity.class);
        oIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(oIntent);
    }

    @Override
    public String getSearch() {
        return getIntent().getStringExtra(HomePresenter.EXTRA_TAG);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getPosition() {
        return m_nPosition;
    }
}
