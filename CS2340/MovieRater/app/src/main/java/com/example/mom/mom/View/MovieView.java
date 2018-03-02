package com.example.mom.mom.View;

import com.example.mom.mom.Model.Movie;

/**
 * Created by Jesse on 3/8/2016.
 */
public interface MovieView {

    String getSearch();

    Movie getMovie();

    float getRating();

    void setTextRating(float fRating);

    void setTitle(String szTitle);

    void setYear(String szYear);

    void setImage(String szURL);
}
