package com.crm.redis_impl.controller.view.refer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReferViewController {

    @GetMapping("/event-and-expo-avail-free-demo")
    public ModelAndView eventAndExpoAvailFreeDemo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enquiry/enquiryForm");
        return modelAndView;
    }
}
