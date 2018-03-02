package com.example.mom.mom.View;

import android.widget.EditText;

/**
 * Created by jesse on 2/29/16.
 */
public interface RegisterView {

    /**
     * Get the EditText associated with password
     * @return edittext desired
     */
    EditText getPasswordText();

    /**
     * Get the EditText associated with password confirm
     * @return edittext desired
     */
    EditText getPasswordConfirmText();

    /**
     * Get username
     * @return username
     */
    String getUsername();

    /**
     * Get password
     * @return password
     */
    String getPassword();

    /**
     * Get password confirmation
     * @return password confirmation
     */
    String getPasswordConfirmation();
}
