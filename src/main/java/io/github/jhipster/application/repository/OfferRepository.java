package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Offer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select offer from Offer offer where offer.owner.login = ?#{principal.username}")
    List<Offer> findByOwnerIsCurrentUser();

}
