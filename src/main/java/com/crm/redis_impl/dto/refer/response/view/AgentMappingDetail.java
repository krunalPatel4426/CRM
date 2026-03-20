package com.crm.redis_impl.dto.refer.response.view;

import lombok.Data;

@Data
public class AgentMappingDetail {
    private Long salesPersonId;
    private String username;
    private String email;
    private String leadSourceName;
}
