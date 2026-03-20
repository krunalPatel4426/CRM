package com.crm.redis_impl.dto.projections.campaign;

public interface CampaignProjection {
    Long getReferId();
    String getUtmSource();
    String getUtmMedium();
    String getUtmCampaign();
}
