package com.epmweb.server.repository;
import com.epmweb.server.domain.StateProvinces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StateProvinces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvincesRepository extends JpaRepository<StateProvinces, Long>, JpaSpecificationExecutor<StateProvinces> {

}
