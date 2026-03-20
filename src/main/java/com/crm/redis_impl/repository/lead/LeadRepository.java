package com.crm.redis_impl.repository.lead;

import com.crm.redis_impl.dto.projections.lead.LeadProjection;
import com.crm.redis_impl.entity.lead.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeadRepository extends JpaRepository<LeadEntity, Long> {
    List<LeadEntity> findBySalespersonIdOrderById(Long salespersonId);
    Optional<LeadEntity> findByIdAndSalespersonId(Long id, Long salespersonId);

    @Query(value = """
        SELECT 
            l.id as id, 
            l.customer_name as customerName, 
            l.phone_number as phoneNumber, 
            l.call_status as callStatus, 
            ls.lead_source_name as leadSourceName 
        FROM leads l 
        LEFT JOIN lead_source ls ON l.lead_source_id = ls.lead_source_id 
        WHERE l.salesperson_id = :salespersonId 
        ORDER BY l.id DESC
        """, nativeQuery = true)
    List<LeadProjection> findLeadsWithSourceBySalespersonId(@Param("salespersonId") Long salespersonId);
}