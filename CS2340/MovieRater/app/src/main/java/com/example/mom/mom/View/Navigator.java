package com.example.mom.mom.View;

/**
 * Created by jesse on 2/29/16.
 */
public interface Navigator {

    /**
     * Starts a new activity
     */
    void startNewActivity(Class oClass);

    void addExtra(String szTag, String szMessage);

    void startNewTopActivity(Class oClass);

    //Todo: Implement this, I think.
    //void putExtra();
}
