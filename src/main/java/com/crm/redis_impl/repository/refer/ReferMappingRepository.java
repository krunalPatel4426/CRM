package com.crm.redis_impl.repository.refer;

import com.crm.redis_impl.entity.refer.ReferMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferMappingRepository extends JpaRepository<ReferMappingEntity, Long> {
    List<ReferMappingEntity> findByRefer_ReferIdOrderByIdAsc(Long referId);

}
