package com.example.mom.mom.View;

import android.widget.ListView;

import org.lucasr.twowayview.TwoWayView;

/**
 * Created by jesse on 2/29/16.
 */
public interface HomeView {

    String getSearch();

    ListView getListViewOverall();

    ListView getListViewMajor();
}
