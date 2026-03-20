package com.crm.redis_impl.dto.leadDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadResponseDto implements Serializable {
    private Long id;
    private String customerName;
    private String phoneNumber;
    private String callStatus;
    private String leadSourceName;
}
