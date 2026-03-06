package com.crm.redis_impl.repository.lead;

import com.crm.redis_impl.entity.lead.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LeadRepository extends JpaRepository<LeadEntity, Long> {
    List<LeadEntity> findBySalespersonIdOrderById(Long salespersonId);
    Optional<LeadEntity> findByIdAndSalespersonId(Long id, Long salespersonId);
}