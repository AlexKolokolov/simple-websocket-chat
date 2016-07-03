package org.kolokolov.chat.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.kolokolov.chat.model.UserProfile;
import org.springframework.stereotype.Component;

/**
 * Created by kolokolov on 7/3/16.
 */
@Component
public class PasswordEncryptor {

    public void encryptPassword(UserProfile user) {
        String md5Hex = DigestUtils.md5Hex(user.getNickname() + user.getPassword());
        user.setPassword(md5Hex);
    }

}
