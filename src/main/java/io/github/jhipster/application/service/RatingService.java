package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rating;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Save a rating.
     *
     * @param rating the entity to save.
     * @return the persisted entity.
     */
    public Rating save(Rating rating) {
        log.debug("Request to save Rating : {}", rating);
        return ratingRepository.save(rating);
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
    }

    /**
     * Get the average rating for a user.
     *
     * @param user the user for whom to search ratings for.
     * @return the the average rating.
     */
    @Transactional(readOnly = true)
    public Double getAverageRatingForUser(User user) {
        log.debug("Request to get average rating for user {}", user);
        return ratingRepository.getAverageRatingForUser(user);
    }
}
