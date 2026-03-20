package com.crm.redis_impl.repository.leadSource;

import com.crm.redis_impl.entity.leadSource.LeadSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadSourceRepository extends JpaRepository<LeadSourceEntity, Long> {

    LeadSourceEntity findByLeadSourceName(String leadSourceName);
}
