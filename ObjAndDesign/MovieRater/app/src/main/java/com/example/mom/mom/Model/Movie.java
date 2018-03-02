package com.example.mom.mom.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesse on 2/18/2016.
 * Nat: changed rating method 3/9/2016
 */
public class Movie {
    String m_szTitle;
    String m_szPosterURL;
    String m_szYear;
    String m_szID;
    float m_fAverageRating;
    Map<User, Float> m_aRatings;

    public Movie(String szTitle, String szPosterURL, String szYear, String szID) {
        m_szTitle = szTitle;
        m_szPosterURL = szPosterURL;
        m_szYear = szYear;
        m_szID = szID;
        m_aRatings = new HashMap<>();
    }

    /**
     * Rates a movie
     * @param oUser user that rated the movie
     * @param fRating rating the user rated
     */
    public void rate(User oUser, float fRating) {

        m_aRatings.put(oUser,fRating);
        m_fAverageRating = calculateRating();
    }

    /**
     * Calculate overall rating
     * @return overall rating
     */
    private float calculateRating() {
        float fAverage = 0;
        //Todo: average smarter
        for (Float f : m_aRatings.values()) {
            fAverage += f;
        }
        fAverage /= m_aRatings.values().size();

        return Float.compare(Float.NaN, fAverage) == 0 ? 0 : fAverage;
    }

    /**
     * Calculate rating based on major
     * @param eMajor major to be based on
     * @return rating
     */
    private float calculateRating(Major eMajor) {
        float fAverage = 0;
        int nCounter = 0;
        //Todo: average smarter
        for (User o : m_aRatings.keySet()) {
            if (o.getMajor().equals(eMajor)) {
                fAverage += m_aRatings.get(o);
                nCounter++;
            }
        }

        fAverage /= nCounter;

        return Float.compare(Float.NaN, fAverage) == 0 ? 0 : fAverage;
    }


    public String getTitle() {
        return m_szTitle;
    }

    public String getPosterURL() {
        return m_szPosterURL;
    }

    public String getYear() {
        return m_szYear;
    }

    public String getID() {
        return m_szID;
    }

    public float getRating() {
        return m_fAverageRating;
    }

    public float getRating(Major eMajor) {
        return calculateRating(eMajor);
    }

    public Map<User, Float> getRatings() {
        return m_aRatings;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie oOther = (Movie) o;
        return (oOther.getTitle().equals(getTitle()) && oOther.getYear().equals(getYear()));
    }
}
