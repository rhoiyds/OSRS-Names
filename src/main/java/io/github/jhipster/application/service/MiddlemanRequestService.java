package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.MiddlemanRequest;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MiddlemanRequest}.
 */
public interface MiddlemanRequestService {

    /**
     * Save a middlemanRequest.
     *
     * @param middlemanRequest the entity to save.
     * @return the persisted entity.
     */
    MiddlemanRequest save(MiddlemanRequest middlemanRequest);

    /**
     * Get all the middlemanRequests.
     *
     * @return the list of entities.
     */
    List<MiddlemanRequest> findAll();


    /**
     * Get the "id" middlemanRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MiddlemanRequest> findOne(Long id);

    /**
     * Delete the "id" middlemanRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the middlemanRequest corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<MiddlemanRequest> search(String query);
}
