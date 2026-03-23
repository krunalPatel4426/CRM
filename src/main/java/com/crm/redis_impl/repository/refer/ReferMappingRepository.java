package com.crm.redis_impl.repository.refer;

import com.crm.redis_impl.entity.refer.ReferMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReferMappingRepository extends JpaRepository<ReferMappingEntity, Long> {

    @Query(nativeQuery = true, value = """
    SELECT ram.* FROM refer_agent_mappings ram 
        join users u on ram.sales_person_id = u.id AND u.is_deleted = 0
    WHERE ram.refer_id=:referId ORDER BY u.id ASC
""")
    List<ReferMappingEntity> findByRefer_ReferIdOrderByIdAsc(Long referId);
}