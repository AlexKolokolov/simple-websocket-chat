package ua.com.chat.model;

import org.springframework.stereotype.Component;

/**
 * Created by kolokolov on 5/14/16.
 */

@Component
public class User {
    private String nickname;
    private String password;

    public User() {}

    public User(String nickname, String password) {
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
