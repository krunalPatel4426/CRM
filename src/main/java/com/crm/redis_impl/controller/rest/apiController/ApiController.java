package com.crm.redis_impl.controller.rest.apiController;

import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.service.apiService.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/free")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/enquiry")
    public ResponseEntity<ApiResponse> sendEnquiry(
            @RequestParam(name = "utm_source") String utmSource,
            @RequestParam(name = "utm_medium") String utmMedium,
            @RequestParam(name = "utm_campaign") String utmCampaign,
            @RequestBody LeadRequestDto request
    ) {
        return apiService.sendEnquiry(utmSource, utmMedium, utmCampaign, request);
    }
}
