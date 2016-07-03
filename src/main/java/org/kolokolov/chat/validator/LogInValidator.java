package org.kolokolov.chat.validator;

import org.kolokolov.chat.model.UserProfile;
import org.kolokolov.chat.service.AccountService;
import org.kolokolov.chat.service.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by kolokolov on 7/2/16.
 */
@Component
public class LogInValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserProfile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserProfile user = (UserProfile) o;
        UserProfile account = accountService.getAccountByLogin(user.getNickname());
        if (account == null) {
            errors.rejectValue("nickname", "error.nickname", "There is no user with such nickname");
        }
        passwordEncryptor.encryptPassword(user);
        if (account != null && !account.getPassword().equals(user.getPassword())) {
            errors.rejectValue("password",
                    "error.password",
                    "Wrong password!");
        }
    }
}
