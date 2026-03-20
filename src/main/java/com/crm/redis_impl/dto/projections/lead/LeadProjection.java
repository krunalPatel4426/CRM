package com.crm.redis_impl.dto.projections.lead;

public interface LeadProjection {
    Long getId();
    String getCustomerName();
    String getPhoneNumber();
    String getCallStatus();
    String getLeadSourceName();
}
