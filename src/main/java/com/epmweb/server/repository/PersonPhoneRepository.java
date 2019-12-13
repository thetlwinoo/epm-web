package com.epmweb.server.repository;
import com.epmweb.server.domain.PersonPhone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone, Long>, JpaSpecificationExecutor<PersonPhone> {

}
