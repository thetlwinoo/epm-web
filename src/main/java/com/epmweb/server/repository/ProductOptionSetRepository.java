package com.epmweb.server.repository;
import com.epmweb.server.domain.ProductOptionSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductOptionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionSetRepository extends JpaRepository<ProductOptionSet, Long>, JpaSpecificationExecutor<ProductOptionSet> {

}
