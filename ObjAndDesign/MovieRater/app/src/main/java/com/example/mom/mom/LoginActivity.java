package com.example.mom.mom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.mom.mom.Model.AuthenticationInterface;
import com.example.mom.mom.Model.LoginManager;
import com.example.mom.mom.Model.Major;
import com.example.mom.mom.Presenter.LoginPresenter;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.View.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public final static String EXTRA_USERNAME = "com.example.mom.mom.USERNAME";

    private String m_szUsername; //Username passed in from register

    private EditText m_oUsername;
    private EditText m_oPassword;
    private LoginPresenter m_oPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.txtUsername).requestFocus();

        //Set appbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get views
        m_oUsername = (EditText) findViewById(R.id.txtUsername);
        m_oPassword = (EditText) findViewById(R.id.txtPassword);

        //Set presenter
        m_oPresenter = new LoginPresenter(this, new LoginManager(), new NavigatorModel(this), this);

        hardcodeInput();

        //Gets username and stuff from other activities
        Intent oIntent = getIntent();
        m_szUsername = oIntent.getStringExtra(LoginActivity.EXTRA_USERNAME);

        if (null != m_szUsername && !m_szUsername.equals("")) {
            EditText username = (EditText) findViewById(R.id.txtUsername);
            username.setText(m_szUsername);
        }
    }

    private void hardcodeInput() {
        //Hardcoded un/pw
        AuthenticationInterface oAuth = new LoginManager();
        oAuth.add("Jesse", "Huang");
        oAuth.add("Nathania", "Nah");
        oAuth.add("Eugenia", "Kim");
        oAuth.add("Nathan", "Whitley");
        oAuth.add("Paul", "Kim");
        oAuth.add("", "", true);

        //Hardcoded bio/major
        LoginManager.getUser("Jesse").setMajor(Major.COMPUTER_SCIENCE);
        LoginManager.getUser("Jesse").setBio("Lorem ipsum dolor sit amet, consectetur \n\n\n\n\n\n\n\nadipiscing elit. Proin tristique auctor justo, eget aliquam nisi tempor commodo. \n\n\n\n\n\nSed vitae magna vitae dolor hendrerit ultricies. Duis tortor velit, sagittis sed condimentum eu, dignissim aliquet odio. Praesent aliquam mi maximus tortor consequat lobortis. Duis maximus eget ipsum id tristique. Aenean vitae rhoncus mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Phasellus non nulla gravida, tincidunt metus vel, lobortis est. Mauris molestie, nibh quis finibus ullamcorper, turpis metus vestibulum diam, at feugiat urna tortor ut ipsum. Nam sodales, tellus sit amet rutrum viverra, eros nisl laoreet libero, non lobortis nisl leo id eros. Sed risus quam, consequat id tempus ac, gravida quis enim. Etiam vel dapibus justo, in porttitor odio. Sed at elementum odio. Vestibulum quis aliquam lectus. ");
    }

    /**
     * On layout click. Clears soft keyboard. //Push
     * @param view view that is passed in
     */
    public void layoutClear(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Tries to authenticate login.
     * @param view view that is passed in
     */
    public void onLoginPress(View view) {
        //Close keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

         m_oPresenter.onClick();
    }

    /**
     * Sends to register screen.
     * @param view view that is passed in.
     */
    public void onRegisterPress(View view) {
        goToRegister();
    }

    /**
     * Transitions to register activity
     */
    private void goToRegister() {
        Intent oIntent = new Intent(this, RegisterActivity.class);
        oIntent.putExtra(EXTRA_USERNAME, getUsername());

        startActivity(oIntent);
    }

    /**
     * Transitions to home activity
     */
    private void goToHome() {
        //Transition to home screen
        Intent oIntent = new Intent(this, HomeActivity.class);
        oIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(oIntent);
    }

    @Override
    public String getUsername() {
        return m_oUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return m_oPassword.getText().toString().trim();
    }
}
