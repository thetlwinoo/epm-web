package com.epmweb.server.repository;
import com.epmweb.server.domain.Photos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Photos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotosRepository extends JpaRepository<Photos, Long>, JpaSpecificationExecutor<Photos> {

}
