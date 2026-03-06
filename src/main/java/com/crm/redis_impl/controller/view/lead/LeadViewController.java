package com.crm.redis_impl.controller.view.lead;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LeadViewController {

    @GetMapping("/leads")
    public ModelAndView leadsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/leads");
        return modelAndView;
    }
}