package com.example.mom.mom.Presenter;

import android.content.Context;

import com.example.mom.mom.Model.User;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;
import com.example.mom.mom.View.UserView;

/**
 * Created by Jesse on 3/14/2016.
 */
public class UserPresenter implements OnCreate {

    private UserView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;

    public UserPresenter(UserView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onCreate() {
        User oUser = m_oView.getUser();
        m_oView.setUsername(oUser.getUsername());
        m_oView.setBio(oUser.getBio());
        m_oView.setStatuses(oUser.getStatuses());


        m_oView.setUnlockEnabled(oUser.getStatuses().contains(User.Status.LOCKED));
        m_oView.setBannedEnabled(!oUser.getStatuses().contains(User.Status.BANNED));
    }

    /**
     * Occurs on ban click
     */
    public void onBanClick() {
        User oUser = m_oView.getUser();
        oUser.removeStatus(User.Status.ACTIVE);
        oUser.addStatuses(User.Status.BANNED);
    }

    /**
     * Occurs on unlock click
     */
    public void onUnlockClick() {
        User oUser = m_oView.getUser();
        oUser.removeStatus(User.Status.LOCKED);
        oUser.addStatuses(User.Status.ACTIVE);
    }
}
