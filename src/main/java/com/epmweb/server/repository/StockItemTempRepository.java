package com.epmweb.server.repository;
import com.epmweb.server.domain.StockItemTemp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockItemTemp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemTempRepository extends JpaRepository<StockItemTemp, Long>, JpaSpecificationExecutor<StockItemTemp> {

}
