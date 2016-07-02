package org.kolokolov.chat.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

/**
 * Created by kolokolov on 5/14/16.
 */

@Component
public class UserProfile {

    @Size(min=3, message = "Nickname must be at least 3 characters long")
    private String nickname;

    @Size(min=6, message = "Password must be at least 6 symbols long")
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
