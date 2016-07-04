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
        String sha1Hex = DigestUtils.sha1Hex(user.getPassword() +
                DigestUtils.sha1Hex(user.getNickname()));
        user.setPassword(sha1Hex);
    }

}
