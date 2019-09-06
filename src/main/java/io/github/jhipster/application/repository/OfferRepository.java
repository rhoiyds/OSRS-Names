package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Offer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Offer entity.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "SELECT o FROM Offer o where o.listing = :listing")
    Offer[] findAllForListing(@Param("listing") Listing listing);

}
