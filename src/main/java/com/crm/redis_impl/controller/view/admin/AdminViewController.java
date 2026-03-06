package com.crm.redis_impl.controller.view.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView manageUsersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/manage-users"); // Maps to /WEB-INF/jsp/admin/manage-users.jsp
        return modelAndView;
    }
}