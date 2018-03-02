package com.example.mom.mom.Presenter;

import com.example.mom.mom.Exception.AlreadyRegisteredException;
import com.example.mom.mom.LoginActivity;
import com.example.mom.mom.Model.AuthenticationInterface;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.Exception.PasswordMismatchException;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.RegisterView;

/**
 * Created by jesse on 2/29/16.
 */
public class RegisterPresenter implements ClickListener {
    private RegisterView m_oView;
    private AuthenticationInterface m_oModel;
    private Navigator m_oNavigator;

    public RegisterPresenter(RegisterView oView, AuthenticationInterface oModel, Navigator oNavigator) {
        m_oView = oView;
        m_oModel = oModel;
        m_oNavigator = oNavigator;
    }

    public void onClick() {
        String szUsername = m_oView.getUsername();
        String szPassword = m_oView.getPassword();
        String szPasswordConfirm = m_oView.getPasswordConfirmation();

        if (szPassword.equals(szPasswordConfirm)) {
            User oCurrent = m_oModel.add(szUsername, szPassword);
            if (null == oCurrent) {
                throw new AlreadyRegisteredException("Username has already been used.");
            } else {
                //Todo: Navigate somewhere
                m_oNavigator.startNewActivity(LoginActivity.class);
            }
        } else {
            throw new PasswordMismatchException("Passwords do not match!");
        }
    }
}
