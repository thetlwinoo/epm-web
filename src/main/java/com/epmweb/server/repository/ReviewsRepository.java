package com.epmweb.server.repository;
import com.epmweb.server.domain.Reviews;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reviews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long>, JpaSpecificationExecutor<Reviews> {

}
