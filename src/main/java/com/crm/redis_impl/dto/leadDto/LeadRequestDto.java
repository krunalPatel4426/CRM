package com.crm.redis_impl.dto.leadDto;

import lombok.Data;

@Data
public class LeadRequestDto {
    private String customerName;
    private String phoneNumber;
    private String callStatus; // e.g., "NEW"
}