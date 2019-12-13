package com.epmweb.server.repository;
import com.epmweb.server.domain.UnitMeasure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnitMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, Long>, JpaSpecificationExecutor<UnitMeasure> {

}
