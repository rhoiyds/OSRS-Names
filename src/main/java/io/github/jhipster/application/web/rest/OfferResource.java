package io.github.jhipster.application.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Offer;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.enumeration.OfferStatus;
import io.github.jhipster.application.domain.enumeration.TradeStatus;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.OfferService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.service.MailService;
import io.github.jhipster.application.service.TradeService;

import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Offer}.
 */
@RestController
@RequestMapping("/api")
public class OfferResource {

    private final Logger log = LoggerFactory.getLogger(OfferResource.class);

    private static final String ENTITY_NAME = "offer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferService offerService;

    private final UserService userService;

    private final TradeService tradeService;

    private final ListingService listingService;

    private final MailService mailService;

    public OfferResource(OfferService offerService, UserService userService, ListingService listingService, MailService mailService, TradeService tradeService) {
        this.offerService = offerService;
        this.userService = userService;
        this.listingService = listingService;
        this.mailService = mailService;
        this.tradeService = tradeService;
    }

    /**
     * {@code POST  /offers} : Create a new offer.
     *
     * @param offer the offer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offer, or with status {@code 400 (Bad Request)} if the offer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offers")
    public ResponseEntity<Offer> createOffer(@Valid @RequestBody Offer offer) throws URISyntaxException {
        log.debug("REST request to save Offer : {}", offer);
        if (offer.getId() != null) {
            throw new BadRequestAlertException("A new offer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> owner = this.userService.getUserWithAuthorities();
        if (!owner.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "notloggeduser");
        }
        offer.setOwner(owner.get());
        offer.setTimestamp(Instant.now());
        offer.setStatus(OfferStatus.PENDING);
        Offer result = offerService.save(offer);
        this.mailService.sendNewOfferMail(offer);
        return ResponseEntity.created(new URI("/api/offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offers} : Updates an existing offer.
     *
     * @param offer the offer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offer,
     * or with status {@code 400 (Bad Request)} if the offer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offers")
    public ResponseEntity<Offer> updateOffer(@Valid @RequestBody Offer offer) throws URISyntaxException {
        log.debug("REST request to update Offer : {}", offer);
        if (offer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<User> currentUser = this.userService.getUserWithAuthorities();
        if (!currentUser.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "notloggeduser");
        }
        Optional<Offer> existingEntity = offerService.findOne(offer.getId());
        if (!existingEntity.get().getOwner().equals(currentUser.get()) && 
        !existingEntity.get().getListing().getOwner().equals(currentUser.get())) {
            throw new BadRequestAlertException("Only the owner of the offer or listing can update the offer", ENTITY_NAME, "owner not updating");
        }
        OfferStatus currentOfferStatus = existingEntity.get().getStatus(); 
        if (!offer.getStatus().equals(offer.getStatus()) && !currentUser.get().equals(offer.getListing().getOwner())) {
            throw new BadRequestAlertException("Only the listing owner can change offer status", ENTITY_NAME, "listing owner not updating");
        }
        offer.setTimestamp(Instant.now());
        Offer result = offerService.save(offer);
        if (result.getStatus().equals(OfferStatus.ACCEPTED) && currentOfferStatus.equals(OfferStatus.PENDING)) {
            Trade trade = new Trade();
            trade.setOffer(result);
            trade.setListingOwnerStatus(TradeStatus.PENDING);
            trade.setOfferOwnerStatus(TradeStatus.PENDING);
            this.tradeService.save(trade);
        }
        this.mailService.sendAnsweredOfferMail(offer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offers} : get all the offers.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offers in body.
     */
    @GetMapping("/offers")
    public List<Offer> getAllOffers() {
        log.debug("REST request to get all Offers");
        return offerService.findAll();
    }

    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offers/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable Long id) {
        log.debug("REST request to get Offer : {}", id);
        Optional<Offer> offer = offerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offer);
    }

    /**
     * {@code GET  /offers/listing/:id} : get the "id" offer.
     *
     * @param id the id of the listing to retrieve all offers for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offers/listing/{id}")
    public List<Offer> getOffersForListing(@PathVariable Long id) {
        log.debug("REST request to get Offers for Listing : {}", id);
        Optional<Listing> existingEntity = listingService.findOne(id);
        if (!existingEntity.isPresent()) {
            throw new BadRequestAlertException("You cannot find offers for a listing that does not exist", ENTITY_NAME, "listing does not exist");
        }
        Optional<User> loggedUser = this.userService.getUserWithAuthorities();
        return Stream.of(offerService.findAllForListing(existingEntity.get())).filter(offer -> {
            return offer.getListing().getOwner().getId().equals(loggedUser.get().getId()) || offer.getOwner().getId().equals(loggedUser.get().getId());
        }).collect(Collectors.toList());
    }

    /**
     * {@code DELETE  /offers/:id} : delete the "id" offer.
     *
     * @param id the id of the offer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        log.debug("REST request to delete Offer : {}", id);
        offerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/offers?query=:query} : search for the offer corresponding
     * to the query.
     *
     * @param query the query of the offer search.
     * @return the result of the search.
     */
    @GetMapping("/_search/offers")
    public List<Offer> searchOffers(@RequestParam String query) {
        log.debug("REST request to search Offers for query {}", query);
        return offerService.search(query);
    }


}
