package org.kolokolov.chat.service;

import org.kolokolov.chat.dao.HibernateDao;
import org.kolokolov.chat.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kolokolov on 7/2/16.
 */
@Component
public class AccountService {
    private final Map<String, UserProfile> LOGIN_TO_PROFILE;
    private final Map<String, UserProfile> SESSION_TO_PROFILE;

    @Autowired
    HibernateDao dao;

    public AccountService() {
        LOGIN_TO_PROFILE = new ConcurrentHashMap<>();
        SESSION_TO_PROFILE = new ConcurrentHashMap<>();
    }

    public void addAccount(UserProfile user) {
        dao.saveUserProfile(user);
    }

    public UserProfile getAccountByLogin(String login) {
        return dao.getUserProfileByNickname(login);
    }

    public void addSessionForUser(UserProfile user, String sessionId) {
        SESSION_TO_PROFILE.put(sessionId, user);
    }

    public UserProfile getAccountBySession(String sessionId) {
        return SESSION_TO_PROFILE.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        SESSION_TO_PROFILE.remove(sessionId);
    }
}
