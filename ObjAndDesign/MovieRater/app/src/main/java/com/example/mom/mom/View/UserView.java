package com.example.mom.mom.View;

import com.example.mom.mom.Model.User;

import java.util.Set;

/**
 * Created by Jesse on 3/14/2016.
 */
public interface UserView {

    User getUser();

    void setUsername(String szUsername);

    void setStatuses(Set<User.Status> aStatuses);

    void setBio(String szBio);

    void setUnlockEnabled(boolean bEnabled);

    void setBannedEnabled(boolean bEnabled);
}
