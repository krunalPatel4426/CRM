package com.crm.redis_impl.controller.rest.referController;

import com.crm.redis_impl.dto.refer.CampaignSetupRequest;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.service.referService.ReferService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaign")
public class ReferController {

    @Autowired
    private ReferService referService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> eventAndExpoAvailFreeDemo(@RequestBody CampaignSetupRequest dto, HttpServletRequest request) {
        return referService.setupCampaign(dto, request);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> campaigns(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return referService.getCampaigns(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> campaign(@PathVariable(name = "id") Long referId) {
        return referService.getCampaign(referId);
    }
}
