package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.MiddlemanRequestService;
import io.github.jhipster.application.domain.MiddlemanRequest;
import io.github.jhipster.application.repository.MiddlemanRequestRepository;
import io.github.jhipster.application.repository.search.MiddlemanRequestSearchRepository;
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
 * Service Implementation for managing {@link MiddlemanRequest}.
 */
@Service
@Transactional
public class MiddlemanRequestServiceImpl implements MiddlemanRequestService {

    private final Logger log = LoggerFactory.getLogger(MiddlemanRequestServiceImpl.class);

    private final MiddlemanRequestRepository middlemanRequestRepository;

    private final MiddlemanRequestSearchRepository middlemanRequestSearchRepository;

    public MiddlemanRequestServiceImpl(MiddlemanRequestRepository middlemanRequestRepository, MiddlemanRequestSearchRepository middlemanRequestSearchRepository) {
        this.middlemanRequestRepository = middlemanRequestRepository;
        this.middlemanRequestSearchRepository = middlemanRequestSearchRepository;
    }

    /**
     * Save a middlemanRequest.
     *
     * @param middlemanRequest the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MiddlemanRequest save(MiddlemanRequest middlemanRequest) {
        log.debug("Request to save MiddlemanRequest : {}", middlemanRequest);
        MiddlemanRequest result = middlemanRequestRepository.save(middlemanRequest);
        middlemanRequestSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the middlemanRequests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MiddlemanRequest> findAll() {
        log.debug("Request to get all MiddlemanRequests");
        return middlemanRequestRepository.findAll();
    }


    /**
     * Get one middlemanRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MiddlemanRequest> findOne(Long id) {
        log.debug("Request to get MiddlemanRequest : {}", id);
        return middlemanRequestRepository.findById(id);
    }

    /**
     * Delete the middlemanRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MiddlemanRequest : {}", id);
        middlemanRequestRepository.deleteById(id);
        middlemanRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the middlemanRequest corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MiddlemanRequest> search(String query) {
        log.debug("Request to search MiddlemanRequests for query {}", query);
        return StreamSupport
            .stream(middlemanRequestSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
