package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.search.TradeSearchRepository;
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
 * Service Implementation for managing {@link Trade}.
 */
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    private final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

    private final TradeRepository tradeRepository;

    private final TradeSearchRepository tradeSearchRepository;

    public TradeServiceImpl(TradeRepository tradeRepository, TradeSearchRepository tradeSearchRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchRepository = tradeSearchRepository;
    }

    /**
     * Save a trade.
     *
     * @param trade the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trade save(Trade trade) {
        log.debug("Request to save Trade : {}", trade);
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the trades.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Trade> findAll() {
        log.debug("Request to get all Trades");
        return tradeRepository.findAll();
    }


    /**
     * Get one trade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trade> findOne(Long id) {
        log.debug("Request to get Trade : {}", id);
        return tradeRepository.findById(id);
    }

    /**
     * Delete the trade by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trade : {}", id);
        tradeRepository.deleteById(id);
        tradeSearchRepository.deleteById(id);
    }

    /**
     * Search for the trade corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Trade> search(String query) {
        log.debug("Request to search Trades for query {}", query);
        return StreamSupport
            .stream(tradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
