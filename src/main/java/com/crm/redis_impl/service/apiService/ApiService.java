package com.crm.redis_impl.service.apiService;

import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ApiService {
    ResponseEntity<ApiResponse> sendEnquiry(String utmSource, String utmMedium, String utmCampaign, LeadRequestDto request);
}
