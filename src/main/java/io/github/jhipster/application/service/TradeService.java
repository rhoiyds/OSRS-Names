package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Trade;

import java.util.List;
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
     * @return the list of entities.
     */
    List<Trade> findAll();


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
     * @return the list of entities.
     */
    List<Trade> search(String query);
}
