package com.crm.redis_impl.service.apiService.Impl;

import com.crm.redis_impl.config.exception.customException.InvalidArgumentException;
import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.entity.refer.ReferEntity;
import com.crm.redis_impl.entity.refer.ReferMappingEntity;
import com.crm.redis_impl.repository.refer.ReferMappingRepository;
import com.crm.redis_impl.repository.refer.ReferRepository;
import com.crm.redis_impl.service.apiService.ApiService;
import com.crm.redis_impl.service.leadService.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired private ReferRepository referRepo;
    @Autowired private ReferMappingRepository mappingRepo;
    @Autowired private LeadService leadService;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> sendEnquiry(String utmSource, String utmMedium, String utmCampaign, LeadRequestDto request) {

        if (utmSource == null || utmMedium == null || utmCampaign == null) {
            throw new InvalidArgumentException("Invalid UTM parameters.");
        }

        ReferEntity refer = referRepo.findByUtmSourceAndUtmMediumAndUtmCampaign(utmSource, utmMedium, utmCampaign);

        if (refer == null) {
            throw new InvalidArgumentException("Campaign not found.");
        }

        List<ReferMappingEntity> mappings = mappingRepo.findByRefer_ReferIdOrderByIdAsc(refer.getReferId());
        if (mappings.isEmpty()) {
            throw new InvalidArgumentException("No agents assigned to this campaign.");
        }

        int currentIndex = refer.getLastAssignedIndex();

        int nextIndex = (currentIndex + 1) % mappings.size();

        ReferMappingEntity selectedMapping = mappings.get(nextIndex);
        Long assignedAgentId = selectedMapping.getSalesPerson().getId();
        String assignedLeadSource = selectedMapping.getLeadSource().getLeadSourceName();

        refer.setLastAssignedIndex(nextIndex);
        Long leadSourceId = referRepo.findLeadSourceId(utmSource, utmMedium, utmCampaign, assignedAgentId);
        referRepo.save(refer);
        request.setLeadSourceId(leadSourceId);

        return leadService.addLead(request, assignedAgentId);
    }
}