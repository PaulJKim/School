package com.example.mom.mom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Presenter.HomePresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.HomeView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    String m_szSearch;

    HomePresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set presenter
        m_oPresenter = new HomePresenter(this, new NavigatorModel(this), this);
        m_oPresenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        //Set search query action
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                m_szSearch = query;

                m_oPresenter.onClick();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    /**
     * Goes to profile view
     * @param view
     */
    public void onProfileClick(View view) {
        onProfilePress();
    }

    /**
     * Goes to profile
     * @param menuItem
     */
    public void onProfilePress(MenuItem menuItem) {
        onProfilePress();
    }

    /**
     * Goes to profile
     */
    private void onProfilePress() {
        Intent oIntent = new Intent(this, ProfileActivity.class);
        startActivity(oIntent);
    }

    /**
     * Logs out and returns to login screen
     * @param menuItem view that sent request
     */
    public void onLogoutPress(MenuItem menuItem) {
        //Popup confirmation
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you want really want log out?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .show();
    }

    /**
     * Logs out and returns to login screen
     */
    private void logout() {

        //Set session
        Session.setUser(null);

        Intent oIntent = new Intent(this, LoginActivity.class);
        oIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(oIntent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    public String getSearch() {
        return m_szSearch;
    }

    @Override
    public ListView getListViewOverall() {
        return (ListView) findViewById(R.id.lvOverall);
    }

    @Override
    public ListView getListViewMajor() {
        return (ListView) findViewById(R.id.lvMajor);
    }
}
