package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Listing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Listing}.
 */
public interface ListingService {

    /**
     * Save a listing.
     *
     * @param listing the entity to save.
     * @return the persisted entity.
     */
    Listing save(Listing listing);

    /**
     * Get all the listings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Listing> findAll(Pageable pageable);

    /**
     * Get all the listings with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Listing> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" listing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Listing> findOne(Long id);

    /**
     * Delete the "id" listing.
     *
     * @param id the id of t
     *           he entity.
     */
    void delete(Long id);

    void changeOutstandingListingsStatus(List<Listing> completedListings, Boolean status);

    Integer getTotalListingsCount();

    Long getTotalBuyingCount();

    Long getTotalSellingCount();

    List<Listing> getMatches(Listing listing);
}
