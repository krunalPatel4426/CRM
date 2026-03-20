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

    @GetMapping("/campaign-setup")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView manageCampaignsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("campaign/campaignSetup");
        return modelAndView;
    }

    @GetMapping("/campaign-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView manageCampaignsList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("campaign/campaignList");
        return modelAndView;
    }

    @GetMapping("/campaign-view")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView manageCampaignsView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("campaign/campaignView");
        return modelAndView;
    }

    @GetMapping("/agent/leads")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView manageLeadsForSpecificAgent() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-agent-leads");
        return modelAndView;
    }
}