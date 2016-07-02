package org.kolokolov.chat.service;

import org.kolokolov.chat.model.UserProfile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kolokolov on 7/2/16.
 */
@Component
public class AccountService {
    private final Map<String, UserProfile> LOGIN_TO_PROFILE;

    public AccountService() {
        LOGIN_TO_PROFILE = new HashMap<>();
    }

    public void addAccount(UserProfile user) {
        this.LOGIN_TO_PROFILE.put(user.getNickname(), user);
    }

    public UserProfile getAccountByLogin(String login) {
        return LOGIN_TO_PROFILE.get(login);
    }
}
