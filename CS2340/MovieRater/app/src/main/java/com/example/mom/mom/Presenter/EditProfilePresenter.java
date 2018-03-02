package com.example.mom.mom.Presenter;

import android.content.Context;

import com.example.mom.mom.EditProfileActivity;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.ProfileActivity;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.EditProfileView;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;

/**
 * Created by jesse on 3/7/16.
 */
public class EditProfilePresenter implements ClickListener, OnCreate {

    private EditProfileView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;

    public EditProfilePresenter(EditProfileView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {
        User oUser = Session.getUser();
        oUser.setBio(m_oView.getBio());
        oUser.setMajor(m_oView.getMajor());
        oUser.setName(m_oView.getName());

        m_oNavigator.startNewTopActivity(ProfileActivity.class);
    }

    @Override
    public void onCreate() {
        User oUser = Session.getUser();
        m_oView.setName(oUser.getName());
        m_oView.setMajor(oUser.getMajor());
        m_oView.setBio(oUser.getBio());
    }
}
