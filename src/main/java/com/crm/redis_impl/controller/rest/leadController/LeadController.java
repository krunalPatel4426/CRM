package com.crm.redis_impl.controller.rest.leadController;

import com.crm.redis_impl.config.exception.customException.UnAuthorizedAccessxception;
import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.repository.lead.LeadRepository;
import com.crm.redis_impl.security.CustomUserDetails;
import com.crm.redis_impl.service.leadService.LeadService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leads")
public class LeadController {

    private final Logger logger = LoggerFactory.getLogger(LeadController.class);

    @Autowired
    private LeadService leadService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getLeads() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long id = customUserDetails.getId();
        if (id == null) {
            logger.error("Invalid user id provided");
            throw new UnAuthorizedAccessxception("You are not logged in");
        }
        logger.info("UserId : {}", id);
        return leadService.getLeadsForSalesperson(id);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createLead(@RequestBody LeadRequestDto request) {
        Long userId = getLoggedInUserId();
        return leadService.addLead(request, userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLead(@PathVariable Long id, @RequestBody LeadRequestDto request) {
        Long userId = getLoggedInUserId();
        return leadService.updateLead(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLead(@PathVariable Long id) {
        Long userId = getLoggedInUserId();
        return leadService.deleteLead(id, userId);
    }

    // Helper method to keep your controller clean
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long id = customUserDetails.getId();
        if (id == null) {
            throw new UnAuthorizedAccessxception("You are not logged in");
        }
        return id;
    }


}
