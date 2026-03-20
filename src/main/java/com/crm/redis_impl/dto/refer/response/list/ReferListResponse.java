package com.crm.redis_impl.dto.refer.response.list;

import lombok.Data;

@Data
public class ReferListResponse {
    private Long referId;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
}
