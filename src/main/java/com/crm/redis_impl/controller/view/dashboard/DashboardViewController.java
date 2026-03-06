package com.crm.redis_impl.controller.view.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DashboardViewController {
    @GetMapping("")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/dashboard");
        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboardView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/leads");
        return modelAndView;
    }
}
