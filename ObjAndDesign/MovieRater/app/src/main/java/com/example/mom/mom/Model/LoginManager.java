package com.example.mom.mom.Model;

import android.util.Log;

import com.example.mom.mom.Exception.IncorrectLoginException;
import com.example.mom.mom.Exception.LockedAccountException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesse on 2/3/2016.
 */
public class LoginManager implements AuthenticationInterface, LoginInterface {
    private static Map<String, User> aUsers = new HashMap<>();

    @Override
    public User add(String szUsername, String szPassword) {
        return add(szUsername, szPassword, false);
    }

    @Override
    public User add(String szUsername, String szPassword, boolean isAdmin) {
        szUsername = szUsername.toLowerCase();

        Log.d("tag", szUsername);
        Log.d("tag", szPassword);
        Log.d("tag", aUsers.toString());

        if (!aUsers.containsKey(szUsername)) {
            //Add to map
            User oCurr = new User(szUsername, szPassword, isAdmin);
            aUsers.put(szUsername, oCurr);
            return oCurr;
        } else {
            return null;
            //User has already registered!

        }
    }

    @Override
    public User remove(String szUsername) {
        szUsername = szUsername.toLowerCase();

        if (aUsers.containsKey(szUsername)) {
            //Remove user
            return aUsers.remove(szUsername);
        } else {
            //No such user
            return null;
        }
    }

    @Override
    public User login(String szUsername, String szPassword) {
        szUsername = szUsername.toLowerCase();

        User oUser = aUsers.get(szUsername);

        // Check exceptions
        if (null == oUser) {
            throw new IncorrectLoginException("Account does not exist.");
        }
        if (oUser.getStatuses().contains(User.Status.LOCKED)) {
            throw new LockedAccountException("Account is locked.");
        }
        if (oUser.getStatuses().contains(User.Status.BANNED)) {
            throw new LockedAccountException("Account is banned.");
        }
        if (!oUser.getPassword().equals(szPassword)) {
            if (oUser.failedLogin()) {
                throw new LockedAccountException("Failed to log in too many times. Account locked.");
            }
            throw new IncorrectLoginException("Username or password is incorrect.");
        }

        oUser.succeededLogin();
        return oUser;
    }

    /**
     * Get user based on username
     * @param szUsername username of user
     * @return User with given username
     */
    public static User getUser(String szUsername) {
        szUsername = szUsername.toLowerCase();
        return aUsers.get(szUsername);
    }

    /**
     * Gets map of users
     * @return all users
     */
    public static Map<String, User> getUsers() {
        return aUsers;
    }
}
