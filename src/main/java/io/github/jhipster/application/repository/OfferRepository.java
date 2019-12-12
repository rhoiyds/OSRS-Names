package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Offer entity.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "select distinct offer from Offer offer left join fetch offer.comments",
        countQuery = "select count(distinct offer) from Offer offer")
    Page<Offer> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct offer from Offer offer left join fetch offer.comments")
    List<Offer> findAllWithEagerRelationships();

    @Query("select offer from Offer offer left join fetch offer.comments where offer.id =:id")
    Optional<Offer> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "SELECT o FROM Offer o where o.listing = :listing")
    Offer[] findAllForListing(@Param("listing") Listing listing);

}
