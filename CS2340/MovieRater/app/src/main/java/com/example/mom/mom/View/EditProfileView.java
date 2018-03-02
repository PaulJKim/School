package com.example.mom.mom.View;

import com.example.mom.mom.Model.Major;

/**
 * Created by jesse on 3/7/16.
 */
public interface EditProfileView {

    void setName(String szName);

    void setBio(String szBio);

    void setMajor(Major eMajor);

    String getName();

    String getBio();

    Major getMajor();
}
