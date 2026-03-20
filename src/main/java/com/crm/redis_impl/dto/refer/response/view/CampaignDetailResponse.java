package com.crm.redis_impl.dto.refer.response.view;

import lombok.Data;

import java.util.List;

@Data
public class CampaignDetailResponse {
    private Long referId;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String referUrl;
    private List<AgentMappingDetail> agentMappings;
}
