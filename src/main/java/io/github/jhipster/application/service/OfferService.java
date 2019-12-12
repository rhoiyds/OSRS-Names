package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Offer}.
 */
public interface OfferService {

    /**
     * Save a offer.
     *
     * @param offer the entity to save.
     * @return the persisted entity.
     */
    Offer save(Offer offer);

    /**
     * Get all the offers.
     *
     * @return the list of entities.
     */
    List<Offer> findAll();

    /**
     * Get all the offers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Offer> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" offer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Offer> findOne(Long id);

    /**
     * Delete the "id" offer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the offer corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Offer> search(String query);

    Offer[] findAllForListing(Listing listing);
}
