package com.epmweb.server.repository;
import com.epmweb.server.domain.ProductCatalog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductCatalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long>, JpaSpecificationExecutor<ProductCatalog> {

}
