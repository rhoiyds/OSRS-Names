package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.enumeration.TradeStatus;
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Trade}.
 */
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    private final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

    private final TradeRepository tradeRepository;

    private final OfferRepository offerRepository;

    public TradeServiceImpl(TradeRepository tradeRepository, OfferRepository offerRepository) {
        this.tradeRepository = tradeRepository;
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
        return tradeRepository.save(trade);
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
    }

    /**
     * Find all completed listings
     */
    @Override
    public List<Listing> getAllCompletedTrades() {
        return tradeRepository.findAllCompletedTrades(TradeStatus.PENDING);
    }
}
