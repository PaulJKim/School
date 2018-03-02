package com.example.mom.mom.Model;

import com.example.mom.mom.Model.User;

/**
 * Created by Jesse on 2/3/2016.
 */
public interface LoginInterface {

    /**
     * Takes the credentials and tests it.
     * @param szUsername username
     * @param szPassword password
     */
    User login(String szUsername, String szPassword);
}
