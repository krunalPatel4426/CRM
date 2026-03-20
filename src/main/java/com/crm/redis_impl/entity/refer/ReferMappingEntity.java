package com.crm.redis_impl.entity.refer;

import com.crm.redis_impl.entity.leadSource.LeadSourceEntity;
import com.crm.redis_impl.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refer_agent_mappings")
@Data
public class ReferMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refer_id", nullable = false)
    private ReferEntity refer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person_id", nullable = false)
    private UserEntity salesPerson;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_source_id", nullable = false)
    private LeadSourceEntity leadSource;
}