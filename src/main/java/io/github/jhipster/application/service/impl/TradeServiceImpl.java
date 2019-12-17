package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.OfferRepository;
import io.github.jhipster.application.repository.search.TradeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    private final OfferRepository offerRepository;

    public TradeServiceImpl(TradeRepository tradeRepository, TradeSearchRepository tradeSearchRepository, OfferRepository offerRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchRepository = tradeSearchRepository;
        this.offerRepository = offerRepository;
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
        Long offerId = trade.getOffer().getId();
        offerRepository.findById(offerId).ifPresent(trade::offer);
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the trades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trade> findAll(Pageable pageable) {
        log.debug("Request to get all Trades");
        return tradeRepository.findAll(pageable);
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
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trade> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trades for query {}", query);
        return tradeSearchRepository.search(queryStringQuery(query), pageable);    }
}
