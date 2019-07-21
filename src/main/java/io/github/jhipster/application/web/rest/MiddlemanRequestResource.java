package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.MiddlemanRequest;
import io.github.jhipster.application.service.MiddlemanRequestService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.MiddlemanRequest}.
 */
@RestController
@RequestMapping("/api")
public class MiddlemanRequestResource {

    private final Logger log = LoggerFactory.getLogger(MiddlemanRequestResource.class);

    private static final String ENTITY_NAME = "middlemanRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MiddlemanRequestService middlemanRequestService;

    public MiddlemanRequestResource(MiddlemanRequestService middlemanRequestService) {
        this.middlemanRequestService = middlemanRequestService;
    }

    /**
     * {@code POST  /middleman-requests} : Create a new middlemanRequest.
     *
     * @param middlemanRequest the middlemanRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new middlemanRequest, or with status {@code 400 (Bad Request)} if the middlemanRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/middleman-requests")
    public ResponseEntity<MiddlemanRequest> createMiddlemanRequest(@Valid @RequestBody MiddlemanRequest middlemanRequest) throws URISyntaxException {
        log.debug("REST request to save MiddlemanRequest : {}", middlemanRequest);
        if (middlemanRequest.getId() != null) {
            throw new BadRequestAlertException("A new middlemanRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MiddlemanRequest result = middlemanRequestService.save(middlemanRequest);
        return ResponseEntity.created(new URI("/api/middleman-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /middleman-requests} : Updates an existing middlemanRequest.
     *
     * @param middlemanRequest the middlemanRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated middlemanRequest,
     * or with status {@code 400 (Bad Request)} if the middlemanRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the middlemanRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/middleman-requests")
    public ResponseEntity<MiddlemanRequest> updateMiddlemanRequest(@Valid @RequestBody MiddlemanRequest middlemanRequest) throws URISyntaxException {
        log.debug("REST request to update MiddlemanRequest : {}", middlemanRequest);
        if (middlemanRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MiddlemanRequest result = middlemanRequestService.save(middlemanRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, middlemanRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /middleman-requests} : get all the middlemanRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of middlemanRequests in body.
     */
    @GetMapping("/middleman-requests")
    public List<MiddlemanRequest> getAllMiddlemanRequests() {
        log.debug("REST request to get all MiddlemanRequests");
        return middlemanRequestService.findAll();
    }

    /**
     * {@code GET  /middleman-requests/:id} : get the "id" middlemanRequest.
     *
     * @param id the id of the middlemanRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the middlemanRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/middleman-requests/{id}")
    public ResponseEntity<MiddlemanRequest> getMiddlemanRequest(@PathVariable Long id) {
        log.debug("REST request to get MiddlemanRequest : {}", id);
        Optional<MiddlemanRequest> middlemanRequest = middlemanRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(middlemanRequest);
    }

    /**
     * {@code DELETE  /middleman-requests/:id} : delete the "id" middlemanRequest.
     *
     * @param id the id of the middlemanRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/middleman-requests/{id}")
    public ResponseEntity<Void> deleteMiddlemanRequest(@PathVariable Long id) {
        log.debug("REST request to delete MiddlemanRequest : {}", id);
        middlemanRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/middleman-requests?query=:query} : search for the middlemanRequest corresponding
     * to the query.
     *
     * @param query the query of the middlemanRequest search.
     * @return the result of the search.
     */
    @GetMapping("/_search/middleman-requests")
    public List<MiddlemanRequest> searchMiddlemanRequests(@RequestParam String query) {
        log.debug("REST request to search MiddlemanRequests for query {}", query);
        return middlemanRequestService.search(query);
    }

}
