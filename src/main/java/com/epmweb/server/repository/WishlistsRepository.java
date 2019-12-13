package com.epmweb.server.repository;
import com.epmweb.server.domain.Wishlists;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wishlists entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WishlistsRepository extends JpaRepository<Wishlists, Long>, JpaSpecificationExecutor<Wishlists> {

}
