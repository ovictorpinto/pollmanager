package com.github.ovictorpinto.pollmanager.controller;

import com.github.ovictorpinto.pollmanager.business.UserBusiness;
import com.github.ovictorpinto.pollmanager.exception.BusinessException;
import com.github.ovictorpinto.pollmanager.helper.SessionHelper;
import com.github.ovictorpinto.pollmanager.model.User;
import com.github.ovictorpinto.pollmanager.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserBusiness userBusiness;
    private final SessionHelper sessionHelper;

    public UserController(UserRepository userRepository, UserBusiness userBusiness, SessionHelper sessionHelper) {
        this.userRepository = userRepository;
        this.userBusiness = userBusiness;
        this.sessionHelper = sessionHelper;
    }

    @GetMapping("user/signup")
    public String loadSignup(Model model) {
        model.addAttribute("user", new User());
        return "user/signup";
    }

    @GetMapping("user/login")
    public String loadLogin(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @Transactional
    @PostMapping("user/login")
    public ModelAndView login(String username, String password, Model model) {
        try {
            User user = userRepository.findBy(username, password);
            userBusiness.validateLogin(user);
            sessionHelper.setUserLogged(user);
            return new ModelAndView("redirect:/poll/list");
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return new ModelAndView("user/login");
        }
    }

    @Transactional
    @PostMapping("user/signup")
    public ModelAndView create(String username, String password, Model model) {
        User user = new User(username, password);
        try {
            userBusiness.validateNewUser(user);
            userRepository.save(user);
            sessionHelper.setUserLogged(user);
            return new ModelAndView("redirect:/poll/list");
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return new ModelAndView("user/signup");
        }
    }

    @GetMapping("user/logout")
    public ModelAndView logout() {
        sessionHelper.logout();
        return new ModelAndView("redirect:/poll/list");
    }
}
