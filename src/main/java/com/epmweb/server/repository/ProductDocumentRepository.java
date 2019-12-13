package com.epmweb.server.repository;
import com.epmweb.server.domain.ProductDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDocumentRepository extends JpaRepository<ProductDocument, Long>, JpaSpecificationExecutor<ProductDocument> {

}
