package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Trade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Trade}.
 */
public interface TradeService {

    /**
     * Save a trade.
     *
     * @param trade the entity to save.
     * @return the persisted entity.
     */
    Trade save(Trade trade);

    /**
     * Get all the trades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trade> findAll(Pageable pageable);


    /**
     * Get the "id" trade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trade> findOne(Long id);

    /**
     * Delete the "id" trade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the trade corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trade> search(String query, Pageable pageable);
}
