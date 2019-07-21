package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rating;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Rating}.
 */
public interface RatingService {

    /**
     * Save a rating.
     *
     * @param rating the entity to save.
     * @return the persisted entity.
     */
    Rating save(Rating rating);

    /**
     * Get all the ratings.
     *
     * @return the list of entities.
     */
    List<Rating> findAll();


    /**
     * Get the "id" rating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rating> findOne(Long id);

    /**
     * Delete the "id" rating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the rating corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Rating> search(String query);
}
