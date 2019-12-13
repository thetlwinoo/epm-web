package com.epmweb.server.repository;
import com.epmweb.server.domain.Cities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitiesRepository extends JpaRepository<Cities, Long>, JpaSpecificationExecutor<Cities> {

}
