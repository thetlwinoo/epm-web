package com.epmweb.server.repository;
import com.epmweb.server.domain.CustomerTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerTransactionsRepository extends JpaRepository<CustomerTransactions, Long>, JpaSpecificationExecutor<CustomerTransactions> {

}
