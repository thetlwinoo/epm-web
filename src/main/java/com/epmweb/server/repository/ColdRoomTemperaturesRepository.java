package com.epmweb.server.repository;
import com.epmweb.server.domain.ColdRoomTemperatures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ColdRoomTemperatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColdRoomTemperaturesRepository extends JpaRepository<ColdRoomTemperatures, Long>, JpaSpecificationExecutor<ColdRoomTemperatures> {

}
