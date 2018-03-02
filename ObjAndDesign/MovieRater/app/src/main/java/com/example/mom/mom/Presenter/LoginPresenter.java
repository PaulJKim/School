package com.example.mom.mom.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.mom.mom.AdminHomeActivity;
import com.example.mom.mom.Exception.BannedAccountException;
import com.example.mom.mom.Exception.IncorrectLoginException;
import com.example.mom.mom.Exception.LockedAccountException;
import com.example.mom.mom.HomeActivity;
import com.example.mom.mom.Model.LoginInterface;
import com.example.mom.mom.Model.LoginManager;
import com.example.mom.mom.Model.Session;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.LoginView;
import com.example.mom.mom.View.Navigator;

/**
 * Created by jesse on 2/29/16.
 */
public class LoginPresenter implements ClickListener {
    private LoginView m_oView;
    private LoginInterface m_oModel;
    private Navigator m_oNagivator;
    private Context m_oContext;

    public LoginPresenter(LoginView oView, LoginInterface oModel, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oModel = oModel;
        m_oNagivator = oNavigator;
        m_oContext = oContext;
    }

    @Override
    public void onClick() {
        String szUsername = m_oView.getUsername();
        String szPassword = m_oView.getPassword();
//        User oTarget = LoginManager.getUser(szUsername);
//        User oCurrent = m_oModel.login(szUsername, szPassword);

        //Todo: Maybe use a try/catch for login?
        try {
            User oUser = m_oModel.login(szUsername, szPassword);
            Session.setUser(oUser);
            if (oUser.getStatuses().contains(User.Status.ADMIN)) {
                m_oNagivator.startNewTopActivity(AdminHomeActivity.class);
            } else {
                m_oNagivator.startNewTopActivity(HomeActivity.class);
            }
        } catch (IncorrectLoginException | LockedAccountException | BannedAccountException e) {
            Toast.makeText(m_oContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        /*if (null == oCurrent || null == oTarget
                || oTarget.getStatuses().contains(User.Status.LOCKED)
                || oTarget.getStatuses().contains(User.Status.BANNED)) {
            //Login failed
            if (oTarget != null) {
                oTarget.failedLogin();
            }
            //Todo: Notify login fail
        } else {
            //Login succeeded
            Session.setUser(oCurrent);
            oCurrent.succeededLogin();
            if (oCurrent.getStatuses().contains(User.Status.ADMIN)) {
                m_oNagivator.startNewTopActivity(AdminHomeActivity.class);
            } else {
                m_oNagivator.startNewTopActivity(HomeActivity.class);
            }
        }*/
    }
}
