package ua.com.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.chat.model.User;

/**
 * Created by Administrator on 17.02.2016.
 */

@Controller
public class MainController {
    private int visitorCount = 0;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "user", new User());
    }

    @RequestMapping("visitors")
    public String visitors(Model model) {
        model.addAttribute("visitor", visitorCount++);
        return "visitor_counter";
    }

    @RequestMapping(value = "chat", method = RequestMethod.POST)
    public ModelAndView chat(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
        }
        return new ModelAndView("chat", "user", user);
    }
}
