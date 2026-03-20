package com.crm.redis_impl.entity.refer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"utm_source", "utm_medium", "utm_campaign"})
})
@Data
public class ReferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refer_id")
    private Long referId;

    @Column(name = "refer_url", columnDefinition = "TEXT")
    private String referUrl;

    @Column(name = "utm_source", nullable = false)
    private String utmSource;

    @Column(name = "utm_medium", nullable = false)
    private String utmMedium;

    @Column(name = "utm_campaign", nullable = false)
    private String utmCampaign;

    @Column(name = "last_assigned_index", nullable = false)
    private Integer lastAssignedIndex = 0;
}