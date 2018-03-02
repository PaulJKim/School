package com.example.mom.mom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mom.mom.Model.NavigatorModel;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.Presenter.AdminUserListPresenter;
import com.example.mom.mom.View.AdminUserListView;
import com.example.mom.mom.View.Navigator;

public class AdminUserListActivity extends AppCompatActivity implements AdminUserListView {

    AdminUserListPresenter m_oPresenter;
    ListView m_lvUsers;
    String m_szUsername;
    LinearLayout m_llCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_userlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_oPresenter = new AdminUserListPresenter(this, new NavigatorModel(this), this);
        m_lvUsers = (ListView) findViewById(R.id.lvUsers);

        m_oPresenter.onCreate();


        //Set onclick
        m_lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_szUsername = ((TextView) view.findViewById(R.id.txtUsername)).getText().toString();
                m_oPresenter.onClick();
            }
        });


        //Add checkboxes
        m_llCheck = (LinearLayout)findViewById(R.id.llCheckForm);

        for (User.Status e : User.Status.values()) {
            CheckBox oCheck = new CheckBox(getApplicationContext());
            oCheck.setText(e.toString());
            oCheck.setChecked(true);
            oCheck.setTextColor(Color.BLACK);
            oCheck.setTag(e);

            oCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    m_oPresenter.onCheck();
                    Log.d("?", buttonView.getTag().toString() + isChecked);
                }
            });
            m_llCheck.addView(oCheck);
        }
    }

    @Override
    public void setAdapter(ArrayAdapter<User> oAdapter) {
        m_lvUsers.setAdapter(oAdapter);
    }

    @Override
    public String getUsername() {
        return m_szUsername;
    }

    @Override
    public LinearLayout getCheckForm() {
        return m_llCheck;
    }

}
