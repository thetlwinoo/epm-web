package com.epmweb.server.repository;
import com.epmweb.server.domain.PurchaseOrders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrdersRepository extends JpaRepository<PurchaseOrders, Long>, JpaSpecificationExecutor<PurchaseOrders> {

}
