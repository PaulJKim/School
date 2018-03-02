package com.example.mom.mom.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jesse on 2/3/2016.
 */
public class User {

    private String m_szUsername;
    private String m_szPassword;
    private String m_szBio;
    private Major m_szMajor;
    private String m_szName;
    private Set<Status> m_aStatuses;

    private int m_nLogins;

    public User(String szUsername, String szPassword) {
        this(szUsername, szPassword, false);
    }

    public User(String szUsername, String szPassword, boolean isAdmin) {
        this.m_szUsername = szUsername;
        this.m_szPassword = szPassword;
        m_szMajor = Major.NO_MAJOR;
        m_aStatuses = new HashSet<>();
        m_aStatuses.add(Status.ACTIVE);
        if (isAdmin) {
            m_aStatuses.add(Status.ADMIN);
        }
        m_nLogins = 0;
    }

    /**
     * User fails login and login count increases.
     * @return
     */
    public boolean failedLogin() {
        m_nLogins++;
        if (m_nLogins >= 3) {
            removeStatus(Status.ACTIVE);
            addStatuses(Status.LOCKED);
            return true;
        }
        return false;
    }

    /**
     * User succeeds login and login count resets
     */
    public void succeededLogin() {
        m_nLogins = 0;
    }

    /**
     * Gets username
     * @return username
     */
    public String getUsername() {
        return m_szUsername;
    }
    /**
     * Sets username. Used in the case of renaming.
     * @param szUsername username to be set
     */
    public void setUsername(String szUsername) {
        m_szUsername = szUsername;
    }

    /**
     * Gets password
     * @return password
     */
    public String getPassword() {
        return m_szPassword;
    }

    /**
     * Sets password
     * @param szPassword password to be set
     */
    public void setPassword(String szPassword) {
        m_szPassword = szPassword;
    }

    /**
     * Gets bio
     * @return bio
     */
    public String getBio() {
        return m_szBio;
    }

    /**
     * Set bio
     * @param szBio Bio to be set
     */
    public void setBio(String szBio) {
        m_szBio = szBio;
    }
    /**
     * Gets major
     * @return major
     */
    public Major getMajor() {
        return m_szMajor;
    }

    /**
     * Set major
     * @param eMajor Major to be set
     */
    public void setMajor(Major eMajor) {
        m_szMajor = eMajor;
    }

    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return m_szName;
    }

    /**
     * Sets name
     * @param szName name to be set
     */
    public void setName(String szName) {
        m_szName = szName;
    }

    /**
     * Get list of statuses
     * @return
     */
    public Set<Status> getStatuses() {
        return m_aStatuses;
    }

    /**
     * Add list of statuses
     * @param aStatuses statuses to be added
     */
    public void addStatuses(Status... aStatuses) {
        for (Status s : aStatuses) {
            m_aStatuses.add(s);
        }
    }

    /**
     *
     * @param eStatus
     * @return
     */
    public boolean removeStatus(Status eStatus) {
        return m_aStatuses.remove(eStatus);
    }


    public enum Status {
        BANNED,
        LOCKED,
        ADMIN,
        ACTIVE
    }

}
