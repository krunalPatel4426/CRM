package com.crm.redis_impl.controller.view.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/register");
        return modelAndView;
    }

    @GetMapping("/change-password")
    public ModelAndView changePasswordPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/change-password"); // Maps to /WEB-INF/jsp/auth/change-password.jsp
        return modelAndView;
    }
}