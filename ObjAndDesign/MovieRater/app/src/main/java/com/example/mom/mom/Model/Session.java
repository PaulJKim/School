package com.example.mom.mom.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesse on 2/12/2016.
 */
public class Session {
    private static User m_oUser;
    private static String m_szSearch;
    private static Movie m_oMovie;
    private static Map<String, Movie> m_aMovies = new HashMap<>();

    public static void setUser(User oUser) {
        m_oUser = oUser;
    }
    public static User getUser() {
        return m_oUser;
    }

    public static String getSearch() {
        return m_szSearch;
    }
    public static void setSearch(String szSearch) {
        m_szSearch = szSearch;
    }

    public static Movie getMovie() {
        return m_oMovie;
    }
    public static void setMovie(Movie oMovie) {
        m_oMovie = oMovie;
    }

    public static Map<String, Movie> getMovies() {
        return m_aMovies;
    }

}
