package com.example.mom.mom.View;

/**
 * Created by Jesse on 3/11/2016.
 */
public interface AdminHomeView {

    ButtonClicked getButton();

    enum ButtonClicked {
        USER,
        ADMIN
    }
}
