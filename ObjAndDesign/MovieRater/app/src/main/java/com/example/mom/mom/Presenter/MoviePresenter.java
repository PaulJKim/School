package com.example.mom.mom.Presenter;

import android.content.Context;

import com.example.mom.mom.HomeActivity;
import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.MovieListActivity;
import com.example.mom.mom.View.BackListener;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.MovieView;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Jesse on 3/8/2016.
 * Added database writing. Nat 3/11/2016
 */
public class MoviePresenter implements ClickListener, OnCreate, BackListener {

    private MovieView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;

    public MoviePresenter(MovieView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {
        m_oView.getMovie().rate(Session.getUser(), m_oView.getRating());
        m_oView.setTextRating(m_oView.getRating());

        Firebase.setAndroidContext(m_oContext);

        final Movie oMovie = m_oView.getMovie();

        User oUser = Session.getUser();
        oMovie.rate(oUser, m_oView.getRating());


        //save data onto firebase
        Firebase ref = new Firebase("https://mom-movie.firebaseio.com/");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap<String, Movie> mMovies = snapshot.child("movies").getValue(HashMap.class);
                mMovies.put(oMovie.getID(), oMovie);

                //save data onto firebase
                Firebase ref = new Firebase("https://mom-movie.firebaseio.com/movies");

                ref.setValue(mMovies, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public void onCreate() {
        m_oView.setTitle(m_oView.getMovie().getTitle());
        m_oView.setYear(m_oView.getMovie().getYear());
        m_oView.setImage(m_oView.getMovie().getPosterURL());

    }

    @Override
    public void onBackPressed() {
        m_oNavigator.addExtra(HomePresenter.EXTRA_TAG, m_oView.getSearch());
        m_oNavigator.startNewTopActivity(MovieListActivity.class);
    }
}
