package com.epmweb.server.repository;
import com.epmweb.server.domain.Invoices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Invoices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, Long>, JpaSpecificationExecutor<Invoices> {

}
