package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RatingService;
import io.github.jhipster.application.domain.Rating;
import io.github.jhipster.application.repository.RatingRepository;
import io.github.jhipster.application.repository.search.RatingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final RatingSearchRepository ratingSearchRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingSearchRepository ratingSearchRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingSearchRepository = ratingSearchRepository;
    }

    /**
     * Save a rating.
     *
     * @param rating the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Rating save(Rating rating) {
        log.debug("Request to save Rating : {}", rating);
        Rating result = ratingRepository.save(rating);
        ratingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ratings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rating> findAll() {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll();
    }


    /**
     * Get one rating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
        ratingSearchRepository.deleteById(id);
    }

    /**
     * Search for the rating corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rating> search(String query) {
        log.debug("Request to search Ratings for query {}", query);
        return StreamSupport
            .stream(ratingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
