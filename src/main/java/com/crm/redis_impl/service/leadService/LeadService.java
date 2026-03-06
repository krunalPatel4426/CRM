package com.crm.redis_impl.service.leadService;

import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
import com.crm.redis_impl.dto.leadDto.LeadResponseDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LeadService {
    ResponseEntity<ApiResponse> getLeadsForSalesperson(Long salespersonId);
    ResponseEntity<ApiResponse> addLead(LeadRequestDto request, Long salespersonId);
    ResponseEntity<ApiResponse> updateLead(Long leadId, LeadRequestDto request, Long salespersonId);
    ResponseEntity<ApiResponse> deleteLead(Long leadId, Long salespersonId);
    List<LeadResponseDto> fetchAndCacheLeads(Long salespersonId);
}