package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rating;
import io.github.jhipster.application.repository.RatingRepository;
import io.github.jhipster.application.repository.search.RatingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    private final RatingSearchRepository ratingSearchRepository;

    public RatingService(RatingRepository ratingRepository, RatingSearchRepository ratingSearchRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingSearchRepository = ratingSearchRepository;
    }

    /**
     * Save a rating.
     *
     * @param rating the entity to save.
     * @return the persisted entity.
     */
    public Rating save(Rating rating) {
        log.debug("Request to save Rating : {}", rating);
        Rating result = ratingRepository.save(rating);
        ratingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Rating> findAll(Pageable pageable) {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll(pageable);
    }


    /**
     * Get one rating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rating> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findById(id);
    }

    /**
     * Delete the rating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
        ratingSearchRepository.deleteById(id);
    }

    /**
     * Search for the rating corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Rating> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ratings for query {}", query);
        return ratingSearchRepository.search(queryStringQuery(query), pageable);    }
}
