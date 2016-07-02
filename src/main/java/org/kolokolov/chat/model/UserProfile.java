package org.kolokolov.chat.model;

import org.springframework.stereotype.Component;

/**
 * Created by kolokolov on 5/14/16.
 */

@Component
public class UserProfile {
    private String nickname;
    private String password;

    public UserProfile() {}

    public UserProfile(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
