package com.example.mom.mom.View;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.mom.mom.Model.User;

/**
 * Created by Jesse on 3/14/2016.
 */
public interface AdminUserListView {
    void setAdapter(ArrayAdapter<User> oAdapter);
    String getUsername();
    LinearLayout getCheckForm();
}
