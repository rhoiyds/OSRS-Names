package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Listing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query(value = "SELECT l FROM Listing l where l.active = TRUE")
    Page<Listing> findAllActive(Pageable pageable);
}
