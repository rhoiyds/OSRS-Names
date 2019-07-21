package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.OfferService;
import io.github.jhipster.application.domain.Offer;
import io.github.jhipster.application.repository.OfferRepository;
import io.github.jhipster.application.repository.search.OfferSearchRepository;
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
 * Service Implementation for managing {@link Offer}.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    private final OfferSearchRepository offerSearchRepository;

    public OfferServiceImpl(OfferRepository offerRepository, OfferSearchRepository offerSearchRepository) {
        this.offerRepository = offerRepository;
        this.offerSearchRepository = offerSearchRepository;
    }

    /**
     * Save a offer.
     *
     * @param offer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Offer save(Offer offer) {
        log.debug("Request to save Offer : {}", offer);
        Offer result = offerRepository.save(offer);
        offerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the offers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Offer> findAll() {
        log.debug("Request to get all Offers");
        return offerRepository.findAll();
    }


    /**
     * Get one offer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id);
    }

    /**
     * Delete the offer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
        offerSearchRepository.deleteById(id);
    }

    /**
     * Search for the offer corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Offer> search(String query) {
        log.debug("Request to search Offers for query {}", query);
        return StreamSupport
            .stream(offerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
