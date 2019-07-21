package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Listing}.
 */
@RestController
@RequestMapping("/api")
public class ListingResource {

    private final Logger log = LoggerFactory.getLogger(ListingResource.class);

    private static final String ENTITY_NAME = "listing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListingService listingService;

    public ListingResource(ListingService listingService) {
        this.listingService = listingService;
    }

    /**
     * {@code POST  /listings} : Create a new listing.
     *
     * @param listing the listing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listing, or with status {@code 400 (Bad Request)} if the listing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/listings")
    public ResponseEntity<Listing> createListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to save Listing : {}", listing);
        if (listing.getId() != null) {
            throw new BadRequestAlertException("A new listing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Listing result = listingService.save(listing);
        return ResponseEntity.created(new URI("/api/listings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /listings} : Updates an existing listing.
     *
     * @param listing the listing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listing,
     * or with status {@code 400 (Bad Request)} if the listing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listings")
    public ResponseEntity<Listing> updateListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to update Listing : {}", listing);
        if (listing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Listing result = listingService.save(listing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, listing.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /listings} : get all the listings.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listings in body.
     */
    @GetMapping("/listings")
    public ResponseEntity<List<Listing>> getAllListings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Listings");
        Page<Listing> page = listingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /listings/:id} : get the "id" listing.
     *
     * @param id the id of the listing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/{id}")
    public ResponseEntity<Listing> getListing(@PathVariable Long id) {
        log.debug("REST request to get Listing : {}", id);
        Optional<Listing> listing = listingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listing);
    }

    /**
     * {@code DELETE  /listings/:id} : delete the "id" listing.
     *
     * @param id the id of the listing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listings/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        log.debug("REST request to delete Listing : {}", id);
        listingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/listings?query=:query} : search for the listing corresponding
     * to the query.
     *
     * @param query the query of the listing search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/listings")
    public ResponseEntity<List<Listing>> searchListings(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Listings for query {}", query);
        Page<Listing> page = listingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
