package org.kolokolov.chat.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kolokolov on 5/14/16.
 */

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    public UserProfile() {}

    public UserProfile(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public UserProfile(String nickname, String password, String confirmPassword) {
        this.nickname = nickname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.created = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "UserProfile{'" + id + "','" + nickname + "', '" + password + "'}";
    }
}
