package com.crm.redis_impl.service.referService.Impl;

import com.crm.redis_impl.config.exception.customException.InvalidArgumentException;
import com.crm.redis_impl.config.exception.customException.NotFoundException;
import com.crm.redis_impl.dto.projections.campaign.CampaignProjection;
import com.crm.redis_impl.dto.refer.CampaignSetupRequest;
import com.crm.redis_impl.dto.refer.response.list.ReferDataTableResponse;
import com.crm.redis_impl.dto.refer.response.list.ReferListResponse;
import com.crm.redis_impl.dto.refer.response.view.AgentMappingDetail;
import com.crm.redis_impl.dto.refer.response.view.CampaignDetailResponse;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.entity.leadSource.LeadSourceEntity;
import com.crm.redis_impl.entity.refer.ReferEntity;
import com.crm.redis_impl.entity.refer.ReferMappingEntity;
import com.crm.redis_impl.entity.user.UserEntity;
import com.crm.redis_impl.repository.leadSource.LeadSourceRepository;
import com.crm.redis_impl.repository.refer.ReferMappingRepository;
import com.crm.redis_impl.repository.refer.ReferRepository;
import com.crm.redis_impl.repository.user.UserRepository;
import com.crm.redis_impl.service.referService.ReferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReferServiceImpl implements ReferService {

    @Autowired private ReferRepository referRepo;
    @Autowired private ReferMappingRepository mappingRepo;
    @Autowired private LeadSourceRepository leadSourceRepo;
    @Autowired private UserRepository userRepo;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> setupCampaign(CampaignSetupRequest request, HttpServletRequest httpRequest) {

        if (referRepo.findByUtmSourceAndUtmMediumAndUtmCampaign(
                request.getUtmSource(), request.getUtmMedium(), request.getUtmCampaign()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponse("false", "This UTM combination already exists.", null));
        }
        for (var mapping : request.getAgentMappings()) {
            if (mapping.getLeadSourceName() == null || mapping.getLeadSourceName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("false", "Lead Source Name cannot be empty.", null));
            }
            if (leadSourceRepo.findByLeadSourceName(mapping.getLeadSourceName().trim()) != null) {
                return ResponseEntity.badRequest().body(new ApiResponse("false", "The Lead Source Name '" + mapping.getLeadSourceName() + "' is already taken. Please choose another.", null));
            }
        }

        String referUrl =  "http://localhost:8080/event-and-expo-avail-free-demo";
        referUrl += "?utm_source=" + request.getUtmSource() + "&utm_medium="+ request.getUtmMedium() + "&utm_campaign=" + request.getUtmCampaign();

        ReferEntity refer = new ReferEntity();
        refer.setUtmSource(request.getUtmSource());
        refer.setUtmMedium(request.getUtmMedium());
        refer.setUtmCampaign(request.getUtmCampaign());
        refer.setReferUrl(referUrl);
        refer = referRepo.save(refer);

        for (var mapping : request.getAgentMappings()) {
            LeadSourceEntity ls = new LeadSourceEntity();
            ls.setLeadSourceName(mapping.getLeadSourceName().trim());
            ls = leadSourceRepo.save(ls);

            UserEntity agent = userRepo.findById(mapping.getSalesPersonId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));

            ReferMappingEntity referMap = new ReferMappingEntity();
            referMap.setRefer(refer);
            referMap.setSalesPerson(agent);
            referMap.setLeadSource(ls);
            mappingRepo.save(referMap);
        }

        return ResponseEntity.ok(new ApiResponse("true", "Campaign mapped successfully!", null));
    }

    @Override
    public ResponseEntity<ApiResponse> getCampaigns(Integer page, Integer size) {
        int currentPage = (page != null && page > 0) ? page-1 : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        int offset = (currentPage) * pageSize;
        List<CampaignProjection> campaignProjections = referRepo.getAllCampaign(offset, pageSize);

        List<ReferListResponse> referListResponse = campaignProjections.stream().map(data -> {
            ReferListResponse refer = new ReferListResponse();
            refer.setReferId(data.getReferId());
            refer.setUtmSource(data.getUtmSource());
            refer.setUtmMedium(data.getUtmMedium());
            refer.setUtmCampaign(data.getUtmCampaign());
            return refer;
        }).toList();

        Integer totalElements = referRepo.countAllCampaign();
        Integer totalPages = totalElements / pageSize;


        ReferDataTableResponse response = new ReferDataTableResponse();
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setReferList(referListResponse);

        return new ResponseEntity<>(new ApiResponse("true", "Campaign mapped successfully!", response), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getCampaign(Long referId) {
        if(referId == null) {
            throw new InvalidArgumentException("referId is null");
        }

        ReferEntity refer = referRepo.findById(referId).orElseThrow(() -> new NotFoundException("Campaign not found"));

        List<ReferMappingEntity> mappings = mappingRepo.findByRefer_ReferIdOrderByIdAsc(refer.getReferId());
        CampaignDetailResponse  response = new CampaignDetailResponse();
        response.setReferUrl(refer.getReferUrl());
        response.setReferId(refer.getReferId());
        response.setUtmSource(refer.getUtmSource());
        response.setUtmMedium(refer.getUtmMedium());
        response.setUtmCampaign(refer.getUtmCampaign());

        List<AgentMappingDetail> agentDetails = mappings.stream().map(mapping -> {
            AgentMappingDetail agent = new AgentMappingDetail();
            agent.setSalesPersonId(mapping.getSalesPerson().getId());
            agent.setUsername(mapping.getSalesPerson().getUsername());
            agent.setEmail(mapping.getSalesPerson().getEmail());
            agent.setLeadSourceName(mapping.getLeadSource().getLeadSourceName());
            return agent;
        }).toList();

        response.setAgentMappings(agentDetails);
        return new ResponseEntity<>(new ApiResponse("true", "Campaign mapped successfully!", response), HttpStatus.OK);

    }

//    @Override
//    public ResponseEntity<ApiResponse> addEventAndExpoAvailFreeDemo(String utmSource, String utmMedium, String utmCampaign, Long salesPersonId) {
//        if(utmSource == null || utmMedium == null || utmCampaign == null) {
//            throw new InvalidArgumentException("utmSource, utmMedium, utmCampaign is invalid.");
//        }
//
//        ReferEntity list = referRepository.findByUtmSourceAndUtmMediumAndUtmCampaignAndSalesPersonId(utmSource, utmMedium, utmCampaign, salesPersonId);
//        if(list != null) {
//            return new ResponseEntity<>(new ApiResponse("true", "Refer Already exists.", null), HttpStatus.OK);
//        }
//
//        ReferEntity referEntity = new ReferEntity();
//        referEntity.setUtmSource(utmSource);
//        referEntity.setUtmMedium(utmMedium);
//        referEntity.setUtmCampaign(utmCampaign);
//        referEntity.setSalesPersonId(salesPersonId);
//        referRepository.save(referEntity);
//
//        String leadSourceName = utmMedium + " " + utmCampaign;
//        LeadSourceEntity leadSourceEntity = leadSourceRepository.findByLeadSourceName(leadSourceName);
//        if(leadSourceEntity == null) {
//            LeadSourceEntity leadSource = new LeadSourceEntity();
//            leadSource.setLeadSourceName(leadSourceName);
//            leadSourceRepository.save(leadSource);
//        }
//
//        return new ResponseEntity<>(new ApiResponse("true", "Enquiry Added.", null), HttpStatus.OK);
//    }
}
