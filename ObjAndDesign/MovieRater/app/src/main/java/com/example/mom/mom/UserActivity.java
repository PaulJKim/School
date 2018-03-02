package com.example.mom.mom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mom.mom.Model.LoginManager;
import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.Presenter.AdminUserListPresenter;
import com.example.mom.mom.Presenter.UserPresenter;
import com.example.mom.mom.View.UserView;

import java.util.Set;

public class UserActivity extends AppCompatActivity implements UserView {

    private UserPresenter m_oPresenter;

    private TextView m_oUsername;
    private TextView m_oStatuses;
    private TextView m_oBio;

    private User m_oUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set textviews
        m_oUsername = (TextView) findViewById(R.id.txtUsername);
        m_oStatuses = (TextView) findViewById(R.id.txtStatuses);
        m_oBio = (TextView) findViewById(R.id.txtBio);

        m_oUser = LoginManager.getUser(getIntent()
                .getStringExtra(AdminUserListPresenter.EXTRA_USERNAME));



        m_oPresenter = new UserPresenter(this, new NavigatorModel(this), this);

        m_oPresenter.onCreate();
    }

    /**
     * Something the user.
     * @param view view that calls this function
     */
    public void onUnlockClick(View view) {
        m_oPresenter.onUnlockClick();
    }

    /**
     * Bans the user.
     * @param view view that calls this function
     */
    public void onBanClick(View view) {
        m_oPresenter.onBanClick();
    }

    @Override
    public User getUser() {
        return m_oUser;
    }

    @Override
    public void setUsername(String szUsername) {
        m_oUsername.setText(szUsername);
    }
    @Override
    public void setStatuses(Set<User.Status> aStatuses) {
        StringBuilder sb = new StringBuilder();
        for (User.Status s : aStatuses) {
            sb.append(s);
            sb.append(" ");
        }

        m_oStatuses.setText(sb.toString());
    }

    @Override
     public void setBio(String szBio) {
        m_oBio.setText(szBio);
    }

    @Override
    public void setUnlockEnabled(boolean bEnabled) {
        Button btnUnlock = (Button) findViewById(R.id.btnUnlock);
        btnUnlock.setEnabled(bEnabled);
    }

    @Override
    public void setBannedEnabled(boolean bEnabled) {
        Button btnBan = (Button) findViewById(R.id.btnBan);
        btnBan.setEnabled(bEnabled);
    }


}
