package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.enumeration.ListingType;
import io.github.jhipster.application.domain.enumeration.TierType;
import io.github.jhipster.application.security.SecurityUtils;
import io.github.jhipster.application.service.ListingQueryService;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.MailService;
import io.github.jhipster.application.service.TagService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.service.dto.ListingCriteria;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.service.filter.BooleanFilter;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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

    private final ListingQueryService listingQueryService;

    private final UserService userService;

    private final TagService tagService;

    private final MailService mailService;

    public ListingResource(ListingService listingService, UserService userService, ListingQueryService listingQueryService, TagService tagService, MailService mailService) {
        this.listingService = listingService;
        this.userService = userService;
        this.listingQueryService = listingQueryService;
        this.tagService = tagService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /listings} : Create a new listing.
     *
     * @param listing the listing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listing, or with status {@code 400 (Bad Request)} if the listing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Transactional
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
     * {@code POST  /listings/add} : Add a new listing.
     *
     * @param listing the listing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listing, or with status {@code 400 (Bad Request)} if the listing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Transactional
    @PostMapping("/listings/add")
    public ResponseEntity<Listing> addListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to save Listing : {}", listing);
        if (listing.getId() != null) {
            throw new BadRequestAlertException("A new listing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> owner = this.userService.getUserWithAuthorities();
        if (!owner.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "notloggeduser");
        }
        Integer totalCountForUser = this.listingService.getTotalListingsCount();
        if ((owner.get().getTier().equals(TierType.FREE) && totalCountForUser >=3) || (owner.get().getTier().equals(TierType.STARTER) && totalCountForUser >=25)) {
            throw new BadRequestAlertException("This user has reached their maximum allowed", ENTITY_NAME, "for their account tier");
        }
        listing.setOwner(owner.get());
        listing.setTimestamp(Instant.now());
        listing.setTags(listing.getTags().stream().map(tag -> tagService.createIfNotExist(tag)).collect(Collectors.toSet()));
        Listing result = listingService.save(listing);
        if (result.getType().equals(ListingType.WANT)) {
            List<User> sentToUser = new ArrayList<>();
            for (Listing l : listingService.getMatches(result)) {
                if (l.getType().equals(ListingType.HAVE)) {
                    if (result.getRsn() == null) {
                        if (!sentToUser.contains(l.getOwner())) {
                            mailService.sendNewCategoryMatchMail(l.getOwner(), result);
                            sentToUser.add(l.getOwner());
                        }
                    } else {
                        mailService.sendNewMatchMail(l);
                    }
                }
            }
        }
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
    @Transactional
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

    @Transactional
    @PutMapping("/listings/edit")
    public ResponseEntity<Listing> editListing(@Valid @RequestBody Listing listing) throws URISyntaxException {
        log.debug("REST request to update Listing : {}", listing);
        if (listing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<User> owner = this.userService.getUserWithAuthorities();
        if (!owner.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "not logged user");
        }
        Optional<Listing> existingEntity = listingService.findOne(listing.getId());
        if (!existingEntity.isPresent() || !existingEntity.get().getOwner().getId().equals(owner.get().getId()) || !existingEntity.get().isActive()) {
            throw new BadRequestAlertException("Only the owner of an entity can update an active entity", ENTITY_NAME, "owner not updating");
        }
        listing.setOwner(owner.get());
        listing.setTimestamp(Instant.now());
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
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listings in body.
     */
    @GetMapping("/listings")
    public ResponseEntity<List<Listing>> getAllListings(ListingCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Listings by criteria: {}", criteria);
        Page<Listing> page = listingQueryService.findByCriteria(criteria, pageable);
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
     * {@code DELETE  /listings/:id} : delete the "id" listing.
     *
     * @param id the id of the listing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listings/{id}/deactivate")
    public ResponseEntity<Void> deactivateListing(@PathVariable Long id) {
        log.debug("REST request to delete Listing : {}", id);
        Optional<Listing> optionalListing = listingService.findOne(id);
        Optional<String> ownerUserName = SecurityUtils.getCurrentUserUsername();
        if (!optionalListing.isPresent()
            || !ownerUserName.isPresent()
            || !ownerUserName.get().equals(optionalListing.get().getOwner().getUsername())
            || !optionalListing.get().isActive()) {
            throw new BadRequestAlertException("Only the owner of an entity can delete an active the entity", ENTITY_NAME, "owner not deleting");
        }
        Listing listing = optionalListing.get();
        listing.setActive(false);
        listingService.save(listing);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

     /**
     * {@code GET  /listings/count} : get the total number of listings for a user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/count")
    public ResponseEntity<Integer> getTotalListingsCount() {
        return ResponseEntity.ok(this.listingService.getTotalListingsCount());
    }

    /**
     * {@code GET  /listings/{id}/matches} : get the matches for a user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/{id}/matches")
    public ResponseEntity<List<Listing>> getMatchesForListing(@PathVariable Long id) {
        log.debug("REST request to get matches for Listing : {}", id);
        Optional<Listing> listing = listingService.findOne(id);
        List<Listing> matches = this.listingService.getMatches(listing.get());
        matches = matches.stream().filter(l -> l.getType().equals(ListingType.WANT)).collect(Collectors.toList());
        return ResponseEntity.ok(matches);
    }

    /**
     * {@code GET  /listings/stats} : get the listing total stats for dashboard display.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        log.debug("REST request to get stats");
        Map<String, Object> stats = new HashMap<String, Object>();
        stats.put("buyingTotal", this.listingService.getTotalBuyingCount());
        stats.put("sellingTotal", this.listingService.getTotalSellingCount());
        stats.put("trending", this.listingService.getLatestTags());
        return ResponseEntity.ok(stats);
    }
}
