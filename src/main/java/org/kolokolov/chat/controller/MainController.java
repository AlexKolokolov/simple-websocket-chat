package org.kolokolov.chat.controller;

import org.kolokolov.chat.service.AccountService;
import org.kolokolov.chat.validator.LogInValidator;
import org.kolokolov.chat.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.kolokolov.chat.model.UserProfile;

/**
 * Created by Administrator on 17.02.2016.
 */
@Controller
public class MainController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private LogInValidator logInValidator;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "user", new UserProfile());
    }

    @RequestMapping(value = "chat", method = RequestMethod.POST)
    public ModelAndView chat(@ModelAttribute("user") UserProfile user, BindingResult bindingResult) {
        logInValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("index", "user", user);
        }
        return new ModelAndView("chat", "user", user);
    }

    @RequestMapping(value = "regForm")
    public ModelAndView registration() {
        return new ModelAndView("regform", "user", new UserProfile());
    }

    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    public ModelAndView sigUp(@ModelAttribute("user") UserProfile user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("regform", "user", user);
        }
        accountService.addAccount(user);
        return new ModelAndView("regconfirm", "user", user);
    }
}