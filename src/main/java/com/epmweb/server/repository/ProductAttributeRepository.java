package com.epmweb.server.repository;
import com.epmweb.server.domain.ProductAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, JpaSpecificationExecutor<ProductAttribute> {

}
