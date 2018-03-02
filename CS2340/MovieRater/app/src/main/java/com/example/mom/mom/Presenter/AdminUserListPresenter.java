package com.example.mom.mom.Presenter;

import android.content.Context;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mom.mom.Model.LoginManager;
import com.example.mom.mom.Model.User;
import com.example.mom.mom.R;
import com.example.mom.mom.UserActivity;
import com.example.mom.mom.View.AdminUserListView;
import com.example.mom.mom.View.ClickListener;
import com.example.mom.mom.View.Navigator;
import com.example.mom.mom.View.OnCreate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jesse on 3/14/2016.
 */
public class AdminUserListPresenter implements ClickListener, OnCreate {

    public static final String EXTRA_USERNAME = "com.example.mom.mom.Presenter.USERNAME";

    private AdminUserListView m_oView;
    private Navigator m_oNavigator;
    private Context m_oContext;
    private ArrayList<User> m_aUsers;
    private ArrayAdapter<User> m_oAdapter;

    public AdminUserListPresenter(AdminUserListView oView, Navigator oNavigator, Context oContext) {
        m_oView = oView;
        m_oNavigator = oNavigator;
        m_oContext = oContext;
        m_aUsers = new ArrayList<>(LoginManager.getUsers().values());
    }

    @Override
    public void onClick() {
        m_oNavigator.addExtra(EXTRA_USERNAME, m_oView.getUsername());
        m_oNavigator.startNewActivity(UserActivity.class);
    }

    @Override
    public void onCreate() {
        m_oAdapter = new UserAdapter(m_oContext, m_aUsers);
        m_oView.setAdapter(m_oAdapter);
    }

    public void onCheck() {
        LinearLayout llForm = m_oView.getCheckForm();

        Set<User.Status> aStatuses = new HashSet<>();
        m_oAdapter.clear();

        //Get checked statuses
        for (User.Status e : User.Status.values()) {
            CheckBox oCheck = (CheckBox) llForm.findViewWithTag(e);
            if (oCheck.isChecked()) {
                aStatuses.add(e);
            }
        }

        //Filter by status
        for (User o : LoginManager.getUsers().values()) {
            addByStatus(aStatuses, o);
        }

        m_oAdapter.notifyDataSetChanged();
    }

    private void addByStatus(Set<User.Status> aStatuses, User oUser) {
        for (User.Status e : aStatuses) {
            if (oUser.getStatuses().contains(e)) {
                m_oAdapter.add(oUser);
                return;
            }
        }
    }

    public class UserAdapter extends ArrayAdapter<User> {

        public UserAdapter(Context oContext, List<User> aUsers) {
            super(oContext, 0, aUsers);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            User oUser = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            }

            // Lookup view for data population
            TextView tvUserName = (TextView) convertView.findViewById(R.id.txtUsername);
            TextView tvName = (TextView) convertView.findViewById(R.id.txtName);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.txtStatus);

            // Fills in texts
            tvUserName.setText(oUser.getUsername());
            if (oUser.getName() != null) {
                tvName.setText("(" + oUser.getName() + ")");
            } else {
                tvName.setText("");
            }


            StringBuilder sb = new StringBuilder();
            for (User.Status s : oUser.getStatuses()) {
                sb.append(s);
                sb.append(" ");
            }
            tvStatus.setText(sb.toString());

            return convertView;
        }
    }
}
