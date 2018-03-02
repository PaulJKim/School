package com.example.mom.mom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Presenter.AdminHomePresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.AdminHomeView;

public class AdminHomeActivity extends AppCompatActivity implements AdminHomeView {

    AdminHomePresenter m_oPresenter;
    ButtonClicked m_oButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set presenter
        m_oPresenter = new AdminHomePresenter(this, new NavigatorModel(this), this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);

        return true;
    }

    @Override
    public ButtonClicked getButton() {
        return m_oButton;
    }

    /**
     * Occurs on profile press on menu. Goes to profile activity.
     * @param item item that called this function
     */
    public void onProfilePress(MenuItem item) {
        Intent oIntent = new Intent(this, ProfileActivity.class);

        startActivity(oIntent);
    }

    //Todo: LogoutListener!
    /**
     * Occurs on logout press on menu. Logs out of app.
     * @param menuItem item that called this function.
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


    /**
     * Continue as user
     * @param view view that called this function
     */
    public void onUserClicked(View view) {
        m_oButton = ButtonClicked.USER;
        m_oPresenter.onClick();
    }

    /**
     * Continue as admin
     * @param view view that called this function
     */
    public void onAdminClicked(View view) {
        m_oButton = ButtonClicked.ADMIN;
        m_oPresenter.onClick();
    }

}
