package com.example.mom.mom.Model;

import com.example.mom.mom.Model.User;

/**
 * Created by Jesse on 2/3/2016.
 */
public interface AuthenticationInterface {

    /**
     * Add a user to the authentication
     * @param szUsername username
     * @param szPassword password
     */
    User add(String szUsername, String szPassword);

    /**
     * Adds a user to the auth
     * @param szUsername username
     * @param szPassword password
     * @param isAdmin whether user is admin
     * @return
     */
    User add(String szUsername, String szPassword, boolean isAdmin);

    /**
     * Removes user
     * @param szUsername username
     * @return User that was removed
     */
    User remove(String szUsername);
}
