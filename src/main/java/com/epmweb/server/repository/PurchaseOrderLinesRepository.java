package com.epmweb.server.repository;
import com.epmweb.server.domain.PurchaseOrderLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrderLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderLinesRepository extends JpaRepository<PurchaseOrderLines, Long>, JpaSpecificationExecutor<PurchaseOrderLines> {

}
