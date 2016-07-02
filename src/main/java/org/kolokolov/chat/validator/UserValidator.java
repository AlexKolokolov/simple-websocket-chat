package org.kolokolov.chat.validator;

import org.kolokolov.chat.model.UserProfile;
import org.kolokolov.chat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by kolokolov on 7/2/16.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserProfile.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "nickname", "error.nickname", "Empty nickname");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.password", "Empty password");
        UserProfile user = (UserProfile) o;
        if (accountService.getAccountByLogin(user.getNickname()) != null) {
            errors.rejectValue("nickname", "error.nickname", "Nickname is already in use");
        }
        if (!user.getNickname().isEmpty() && user.getNickname().length() < 3) {
            errors.rejectValue("nickname",
                    "error.nickname",
                    "Nickname must be at least 3 characters long");
        }
        if (!user.getPassword().isEmpty() && user.getPassword().length() < 6) {
            errors.rejectValue("password",
                    "error.password",
                    "Password must be at least 6 symbols long");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword",
                    "error.confirmPassword",
                    "Passwords must match");
        }
    }
}
