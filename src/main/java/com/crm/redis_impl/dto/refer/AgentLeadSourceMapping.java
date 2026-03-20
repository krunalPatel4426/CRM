package com.crm.redis_impl.dto.refer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentLeadSourceMapping {
    private Long salesPersonId;
    private String leadSourceName;
}
