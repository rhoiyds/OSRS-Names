package io.github.jhipster.application.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.search.TradeSearchRepository;
import io.github.jhipster.application.service.dto.TradeCriteria;

/**
 * Service for executing complex queries for {@link Trade} entities in the database.
 * The main input is a {@link TradeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Trade} or a {@link Page} of {@link Trade} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TradeQueryService extends QueryService<Trade> {

    private final Logger log = LoggerFactory.getLogger(TradeQueryService.class);

    private final TradeRepository tradeRepository;

    private final TradeSearchRepository tradeSearchRepository;

    public TradeQueryService(TradeRepository tradeRepository, TradeSearchRepository tradeSearchRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchRepository = tradeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Trade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Trade> findByCriteria(TradeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Trade> specification = createSpecification(criteria);
        return tradeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Trade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Trade> findByCriteria(TradeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Trade> specification = createSpecification(criteria);
        return tradeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TradeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Trade> specification = createSpecification(criteria);
        return tradeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Trade> createSpecification(TradeCriteria criteria) {
        Specification<Trade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Trade_.id));
            }
            if (criteria.getListingOwnerStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getListingOwnerStatus(), Trade_.listingOwnerStatus));
            }
            if (criteria.getOfferOwnerStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferOwnerStatus(), Trade_.offerOwnerStatus));
            }
            if (criteria.getOfferId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferId(),
                    root -> root.join(Trade_.offer, JoinType.LEFT).get(Offer_.id)));
            }
        }
        return specification;
    }
}
