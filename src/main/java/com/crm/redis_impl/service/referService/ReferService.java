package com.crm.redis_impl.service.referService;

import com.crm.redis_impl.dto.refer.CampaignSetupRequest;
import com.crm.redis_impl.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ReferService {
//    ResponseEntity<ApiResponse> addEventAndExpoAvailFreeDemo(String utmSource, String utmMedium, String utmCampaign, Long salesPersonId);
public ResponseEntity<ApiResponse> setupCampaign(CampaignSetupRequest request, HttpServletRequest httpRequest);

    ResponseEntity<ApiResponse> getCampaigns(Integer page, Integer size);

    ResponseEntity<ApiResponse> getCampaign(Long referId);
}
