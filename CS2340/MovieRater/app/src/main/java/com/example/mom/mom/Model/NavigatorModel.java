package com.example.mom.mom.Model;

import android.content.Context;
import android.content.Intent;

import com.example.mom.mom.Model.Movie;
import com.example.mom.mom.View.Navigator;

/**
 * Created by jesse on 2/29/16.
 */
public class NavigatorModel implements Navigator {

    private Context m_oContext;
    private Intent m_oIntent;

    public NavigatorModel(Context oContext) {
        m_oContext = oContext;
        m_oIntent = new Intent(oContext, Movie.class);
    }

    @Override
    public void startNewActivity(Class oClass) {
        m_oIntent.setClass(m_oContext, oClass);
        m_oContext.startActivity(m_oIntent);
    }

    @Override
    public void addExtra(String szTag, String szMessage) {
        m_oIntent.putExtra(szTag, szMessage);
    }

    @Override
    public void startNewTopActivity(Class oClass) {
        m_oIntent.setClass(m_oContext, oClass);

        m_oIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        m_oContext.startActivity(m_oIntent);
    }
}
