package com.epmweb.server.repository;
import com.epmweb.server.domain.DangerousGoods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DangerousGoods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DangerousGoodsRepository extends JpaRepository<DangerousGoods, Long>, JpaSpecificationExecutor<DangerousGoods> {

}
