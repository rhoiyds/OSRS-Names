package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Offer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "SELECT o FROM Offer o where o.listing = :listing")
    Offer[] findAllForListing(@Param("listing") Listing listing);

    @Query("select offer from Offer offer where offer.owner.username = ?#{principal.username}")
    List<Offer> findByOwnerIsCurrentUser();

}
