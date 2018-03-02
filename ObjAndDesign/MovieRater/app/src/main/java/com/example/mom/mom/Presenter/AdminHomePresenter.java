package com.example.mom.mom.Presenter;

import android.content.Context;

import com.example.mom.mom.AdminUserListActivity;
import com.example.mom.mom.HomeActivity;
import com.example.mom.mom.View.AdminHomeView;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.Navigator;

/**
 * Created by Jesse on 3/11/2016.
 */
public class AdminHomePresenter implements ClickListener{

    private AdminHomeView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;

    /**
     * Create a new Admin Home Presenter
     * @param oView Admin Home View
     * @param oNavigator Navigator to new activities
     * @param oContext Context
     */
    public AdminHomePresenter(AdminHomeView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {
        switch (m_oView.getButton()) {
            case USER:
                continueUser();
                break;
            case ADMIN:
                continueAdmin();
                break;
            default:
                continueUser();
                break;
        }
    }

    /**
     * Goes to user activity
     */
    private void continueUser() {
        m_oNavigator.startNewActivity(HomeActivity.class);
    }

    /**
     * Goes to admin activity
     */
    private void continueAdmin() {
        m_oNavigator.startNewActivity(AdminUserListActivity.class);
    }
}
