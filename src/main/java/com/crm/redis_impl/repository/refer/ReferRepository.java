package com.crm.redis_impl.repository.refer;

import com.crm.redis_impl.dto.projections.campaign.CampaignProjection;
import com.crm.redis_impl.entity.refer.ReferEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferRepository extends JpaRepository<ReferEntity, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * 
            from refers 
            where 
                utm_source=:utmSource 
              AND 
                utm_medium=:utmMedium 
              AND 
                utm_campaign=:utmCampaign 
              AND 
                sales_person_id=:utmId 
            LIMIT 1;
""")
    ReferEntity findByUtmSourceAndUtmMediumAndUtmCampaignAndSalesPersonId(String  utmSource, String utmMedium, String utmCampaign, Long utmId);

    @Query(nativeQuery = true, value = """
        SELECT sales_person_id
            from refers 
            where 
                utm_source=:utmSource 
              AND 
                utm_medium=:utmMedium 
              AND 
                utm_campaign=:utmCampaign
            LIMIT 1;
""")
    Long findSalesPersonId(String utmSource,String utmMedium,String utmCampaign);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ReferEntity findByUtmSourceAndUtmMediumAndUtmCampaign(String src, String med, String camp);


    @Query(nativeQuery = true, value = """
        SELECT 
            refer_id as referId,
            utm_source as utmSource,
            utm_medium as utmMedium,
            utm_campaign as utmCampaign
        FROM refers 
            ORDER BY refer_id desc OFFSET :offset LIMIT :limit;
""")
    List<CampaignProjection> getAllCampaign(Integer offset, Integer limit);

    @Query(nativeQuery = true, value = """
    SELECT COUNT(*) FROM refers;
""")
    Integer countAllCampaign();



    @Query(nativeQuery = true, value = """
        SELECT 
            ram.lead_source_id 
        FROM refers r 
            JOIN refer_agent_mappings ram ON r.refer_id = ram.refer_id AND ram.sales_person_id=:salesPersonId
            JOIN users u on ram.sales_person_id = u.id AND u.is_deleted = 0
        WHERE r.utm_source=:utmSource AND r.utm_medium=:utmMedium AND r.utm_campaign=:utmCampaign;
""")
    Long findLeadSourceId(String  utmSource,String utmMedium,String utmCampaign, Long salesPersonId);

}
