package com.crm.redis_impl.entity.leadSource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lead_source")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeadSourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_source_id")
    private Long leadSourceId;

    @Column(name = "lead_source_name", columnDefinition = "TEXT", unique = true)
    private String leadSourceName;
}
