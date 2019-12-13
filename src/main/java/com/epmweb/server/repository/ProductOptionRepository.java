package com.epmweb.server.repository;
import com.epmweb.server.domain.ProductOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, JpaSpecificationExecutor<ProductOption> {

}
