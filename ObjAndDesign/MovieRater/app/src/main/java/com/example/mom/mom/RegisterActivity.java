package com.example.mom.mom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mom.mom.Exception.AlreadyRegisteredException;
import com.example.mom.mom.Exception.PasswordMismatchException;
import com.example.mom.mom.Model.LoginManager;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.Presenter.RegisterPresenter;
import com.example.mom.mom.View.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    String m_szUsername; //Username passed in from login

    //Views
    private EditText m_oUsername;
    private EditText m_oPassword;
    private EditText m_oPasswordConfirm;

    //Presenter
    RegisterPresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get views
        m_oUsername = (EditText) findViewById(R.id.txtUsername);
        m_oPassword = (EditText) findViewById(R.id.txtPassword);
        m_oPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);

        //Get presenter
        m_oPresenter = new RegisterPresenter(this, new LoginManager(), new NavigatorModel(this));


        // Fill in username
        Intent intent = getIntent();
        m_szUsername = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);

        if (null != m_szUsername && !m_szUsername.equals("")) {
            m_oUsername.setText(m_szUsername);
            m_oPassword.requestFocus();
        } else {
            m_oUsername.requestFocus();
        }
    }

    /**
     * Tries to register an account.
     * @param view view that called the thing
     */
    public void onRegisterPress(View view) {
        //Close keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        try {
            m_oPresenter.onClick();
        } catch (AlreadyRegisteredException | PasswordMismatchException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to login screen.
     * @param view
     */
    public void onLoginPress(View view) {
        returnToLogin();
    }

    /**
     * Layout press. Closes keyboard.
     * @param view
     */
    public void onLayoutPress(View view) {
        //Close keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Returns to login screen.
     */
    private void returnToLogin() {
        EditText username = (EditText) findViewById(R.id.txtUsername);

        Intent oIntent = new Intent(this, LoginActivity.class);
        oIntent.putExtra(LoginActivity.EXTRA_USERNAME, username.getText().toString());
        oIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(oIntent);
    }

    @Override
    public String getUsername() {
        return m_oUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return m_oPassword.getText().toString();
    }

    @Override
    public String getPasswordConfirmation() {
        return m_oPasswordConfirm.getText().toString();
    }

    @Override
    public EditText getPasswordText() {
        return m_oPassword;
    }

    @Override
    public EditText getPasswordConfirmText() {
        return m_oPasswordConfirm;
    }
}
