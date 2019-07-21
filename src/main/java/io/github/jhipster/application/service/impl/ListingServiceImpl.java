package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.repository.ListingRepository;
import io.github.jhipster.application.repository.search.ListingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Listing}.
 */
@Service
@Transactional
public class ListingServiceImpl implements ListingService {

    private final Logger log = LoggerFactory.getLogger(ListingServiceImpl.class);

    private final ListingRepository listingRepository;

    private final ListingSearchRepository listingSearchRepository;

    public ListingServiceImpl(ListingRepository listingRepository, ListingSearchRepository listingSearchRepository) {
        this.listingRepository = listingRepository;
        this.listingSearchRepository = listingSearchRepository;
    }

    /**
     * Save a listing.
     *
     * @param listing the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Listing save(Listing listing) {
        log.debug("Request to save Listing : {}", listing);
        Listing result = listingRepository.save(listing);
        listingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the listings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Listing> findAll(Pageable pageable) {
        log.debug("Request to get all Listings");
        return listingRepository.findAll(pageable);
    }


    /**
     * Get one listing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Listing> findOne(Long id) {
        log.debug("Request to get Listing : {}", id);
        return listingRepository.findById(id);
    }

    /**
     * Delete the listing by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Listing : {}", id);
        listingRepository.deleteById(id);
        listingSearchRepository.deleteById(id);
    }

    /**
     * Search for the listing corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Listing> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Listings for query {}", query);
        return listingSearchRepository.search(queryStringQuery(query), pageable);    }
}
