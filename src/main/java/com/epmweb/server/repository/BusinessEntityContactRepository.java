package com.epmweb.server.repository;
import com.epmweb.server.domain.BusinessEntityContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessEntityContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityContactRepository extends JpaRepository<BusinessEntityContact, Long>, JpaSpecificationExecutor<BusinessEntityContact> {

}
