// DTOs
package com.crm.redis_impl.dto.refer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignSetupRequest {
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private List<AgentLeadSourceMapping> agentMappings;
}