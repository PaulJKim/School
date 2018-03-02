package com.example.mom.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mom.mom.Model.Major;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.Presenter.ProfilePresenter;
import com.example.mom.mom.View.ProfileView;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    User m_oCurrentUser; //User that has logged in
    ProfilePresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setting presenter
        m_oPresenter = new ProfilePresenter(this, new NavigatorModel(this), this);

        //Set current user
        m_oCurrentUser = Session.getUser();

        m_oPresenter.onCreate();

        //Turns scroll on Bio on
        TextView txtBio = (TextView) findViewById(R.id.txtBio);
        txtBio.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public void setName(String szName) {
        TextView txtUsername = (TextView) findViewById(R.id.txtName);
        txtUsername.setText(szName);
    }

    @Override
    public void setBio(String szBio) {
        TextView txtBio = (TextView) findViewById(R.id.txtBio);
        txtBio.setText(szBio);
    }

    @Override
    public void setMajor(Major eMajor) {
        TextView txtMajor = (TextView) findViewById(R.id.txtMajor);
        txtMajor.setText(eMajor.toString());
    }


    /**
     * Logs out and returns to login screen
     * @param view view that sent request
     */
    public void onLogoutPress(View view) {
        m_oPresenter.onLogout();
    }

    /**
     * Logs out and returns to login screen
     * @param item item that sent request
     */
    public void onLogoutPress(MenuItem item) {
        //Popup confirmation
        m_oPresenter.onLogout();
    }

    /**
     * Goes to edit profile activity
     * @param view View that calls this method
     */
    public void onEditProfilePress(View view) {
        editProfile();
    }

    /**
     * Goes to edit profile activity
     * @param item Item that calls this method
     */
    public void onEditProfilePress(MenuItem item) {
        editProfile();
    }

    /**
     * Goes to edit profile activity
     */
    private void editProfile() {
        Intent oIntent = new Intent(this, EditProfileActivity.class);
        //oIntent.putExtra(LoginActivity.EXTRA_USERNAME, m_oCurrentUser.getUsername());

        startActivity(oIntent);
    }
}
