    package com.crm.redis_impl.service.leadService.impl;

    import com.crm.redis_impl.dto.leadDto.LeadRequestDto;
    import com.crm.redis_impl.dto.leadDto.LeadResponseDto;
    import com.crm.redis_impl.dto.response.ApiResponse;
    import com.crm.redis_impl.entity.lead.LeadEntity;
    import com.crm.redis_impl.entity.leadSource.LeadSourceEntity;
    import com.crm.redis_impl.entity.user.UserEntity;
    import com.crm.redis_impl.repository.lead.LeadRepository;
    import com.crm.redis_impl.repository.leadSource.LeadSourceRepository;
    import com.crm.redis_impl.repository.user.UserRepository;
    import com.crm.redis_impl.service.leadService.LeadService;
    import com.crm.redis_impl.service.redisService.RedisService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Service
    public class LeadServiceImpl implements LeadService {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Autowired
        private LeadRepository leadRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        @Lazy
        private LeadService self;

        @Autowired
        private RedisService redisService;

        @Autowired
        private LeadSourceRepository  leadSourceRepository;

        @Override
        public ResponseEntity<ApiResponse> getLeadsForSalesperson(Long salespersonId) {

            List<LeadResponseDto> leads = redisService.get("salesperson_leads::"+salespersonId, List.class);
            if (leads == null || leads.isEmpty()) {
                leads = self.fetchAndCacheLeads(salespersonId);
                redisService.set("salesperson_leads::"+salespersonId, leads, 3600L);
            }
            return new ResponseEntity<>(new ApiResponse("true", "Leads fetched successfully", leads), HttpStatus.OK);
        }

        @Override
    //    @Cacheable(value = "salesperson_leads", key = "#salespersonId")
        public List<LeadResponseDto> fetchAndCacheLeads(Long salespersonId) {
            logger.info(">>> Cache Miss! Fetching leads from PostgreSQL database for User ID: {}", salespersonId);

            List<LeadEntity> leads = leadRepository.findBySalespersonIdOrderById(salespersonId);

            return leads.stream().map(lead -> {
                LeadResponseDto dto = new LeadResponseDto();
                dto.setId(lead.getId());
                dto.setCustomerName(lead.getCustomerName());
                if(lead.getLeadSource() != null) {
                    dto.setLeadSourceName(lead.getLeadSource().getLeadSourceName());
                }else{
                    dto.setLeadSourceName("Manual/Direct");
                }
                dto.setPhoneNumber(lead.getPhoneNumber());
                dto.setCallStatus(lead.getCallStatus());
                return dto;
            }).collect(Collectors.toList());
        }



        @Override
    //    @CacheEvict(value = "salesperson_leads", key = "#salespersonId")
        public ResponseEntity<ApiResponse> addLead(LeadRequestDto request, Long salespersonId) {
            UserEntity salesperson = userRepository.findById(salespersonId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            LeadSourceEntity leadSourceEntity;
            Optional<LeadSourceEntity> optionalLeadSourceEntity;
            if (request.getLeadSourceId() != null) {
                 optionalLeadSourceEntity = leadSourceRepository.findById(request.getLeadSourceId());
                if(!optionalLeadSourceEntity.isPresent()) {
                    leadSourceEntity = null;
                }else{
                    leadSourceEntity = optionalLeadSourceEntity.get();
                }
            }else {
                leadSourceEntity = null;
            }


            LeadEntity newLead = new LeadEntity();
            newLead.setCustomerName(request.getCustomerName());
            newLead.setPhoneNumber(request.getPhoneNumber());
            newLead.setCallStatus(request.getCallStatus() != null ? request.getCallStatus() : "NEW");
            newLead.setLeadSource(leadSourceEntity);
            newLead.setSalesperson(salesperson);

            leadRepository.save(newLead);
            redisService.delete("salesperson_leads::"+salespersonId);
            return new ResponseEntity<>(new ApiResponse("true", "Lead added successfully", null), HttpStatus.CREATED);
        }

        @Override
    //    @CacheEvict(value = "salesperson_leads", key = "#salespersonId")
        public ResponseEntity<ApiResponse> updateLead(Long leadId, LeadRequestDto request, Long salespersonId) {
            LeadEntity existingLead = leadRepository.findByIdAndSalespersonId(leadId, salespersonId)
                    .orElseThrow(() -> new RuntimeException("Lead not found or you do not have permission to edit it"));

            existingLead.setCustomerName(request.getCustomerName());
            existingLead.setPhoneNumber(request.getPhoneNumber());
            existingLead.setCallStatus(request.getCallStatus());

            leadRepository.save(existingLead);
            redisService.delete("salesperson_leads::"+salespersonId);
            return new ResponseEntity<>(new ApiResponse("true", "Lead updated successfully", null), HttpStatus.OK);
        }

        @Override
    //    @CacheEvict(value = "salesperson_leads", key = "#salespersonId")
        public ResponseEntity<ApiResponse> deleteLead(Long leadId, Long salespersonId) {
            LeadEntity existingLead = leadRepository.findByIdAndSalespersonId(leadId, salespersonId)
                    .orElseThrow(() -> new RuntimeException("Lead not found or you do not have permission to delete it"));

            leadRepository.delete(existingLead);
            redisService.delete("salesperson_leads::"+salespersonId);
            return new ResponseEntity<>(new ApiResponse("true", "Lead deleted successfully", null), HttpStatus.OK);
        }
    }