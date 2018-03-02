package com.example.mom.mom.Presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.mom.mom.LoginActivity;
import com.example.mom.mom.Model.Major;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.View.LogoutListener;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;
import com.example.mom.mom.View.ProfileView;

/**
 * Created by jesse on 3/7/16.
 */
public class ProfilePresenter implements LogoutListener, OnCreate {

    ProfileView m_oView;
    Navigator m_oNavigator;
    Context m_oContext;

    public ProfilePresenter(ProfileView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onCreate() {
        User oUser = Session.getUser();
        if (null != oUser.getName() && !oUser.getName().equals("")) {
            m_oView.setName(Session.getUser().getName());
        } else {
            m_oView.setName(Session.getUser().getUsername());
        }
        m_oView.setBio(Session.getUser().getBio());
        m_oView.setMajor(Session.getUser().getMajor());
    }

    @Override
    public void onLogout() {
        new AlertDialog.Builder(m_oContext)
                .setTitle("Logout")
                .setMessage("Do you really want to log out?")
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

        m_oNavigator.startNewTopActivity(LoginActivity.class);
    }
}
