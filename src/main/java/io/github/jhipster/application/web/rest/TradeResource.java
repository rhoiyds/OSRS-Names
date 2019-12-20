package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.Rating;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.enumeration.TradeStatus;
import io.github.jhipster.application.service.*;
import io.github.jhipster.application.service.dto.TradeConfirmDTO;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.TradeCriteria;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Trade}.
 */
@RestController
@RequestMapping("/api")
public class TradeResource {

    private final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private static final String ENTITY_NAME = "trade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeService tradeService;

    private final TradeQueryService tradeQueryService;

    private final UserService userService;

    private final RatingService ratingService;

    private final ListingService listingService;

    public TradeResource(TradeService tradeService, TradeQueryService tradeQueryService, UserService userService, RatingService ratingService, ListingService listingService) {
        this.tradeService = tradeService;
        this.tradeQueryService = tradeQueryService;
        this.userService = userService;
        this.ratingService = ratingService;
        this.listingService = listingService;
    }

    /**
     * {@code POST  /trades} : Create a new trade.
     *
     * @param trade the trade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trade, or with status {@code 400 (Bad Request)} if the trade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trades")
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to save Trade : {}", trade);
        if (trade.getId() != null) {
            throw new BadRequestAlertException("A new trade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(trade.getOffer())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Trade result = tradeService.save(trade);
        return ResponseEntity.created(new URI("/api/trades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trades} : Updates an existing trade.
     *
     * @param trade the trade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trade,
     * or with status {@code 400 (Bad Request)} if the trade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trades")
    public ResponseEntity<Trade> updateTrade(@RequestBody Trade trade) throws URISyntaxException {
        log.debug("REST request to update Trade : {}", trade);
        if (trade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trade result = tradeService.save(trade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trade.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trades} : get all the trades.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trades in body.
     */
    @GetMapping("/trades")
    public ResponseEntity<List<Trade>> getAllTrades(TradeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Trades by criteria: {}", criteria);
        Page<Trade> page = tradeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /trades/count} : count all the trades.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/trades/count")
    public ResponseEntity<Long> countTrades(TradeCriteria criteria) {
        log.debug("REST request to count Trades by criteria: {}", criteria);
        return ResponseEntity.ok().body(tradeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trades/:id} : get the "id" trade.
     *
     * @param id the id of the trade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trades/{id}")
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {
        log.debug("REST request to get Trade : {}", id);
        Optional<Trade> trade = tradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trade);
    }

    /**
     * {@code DELETE  /trades/:id} : delete the "id" trade.
     *
     * @param id the id of the trade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trades/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        log.debug("REST request to delete Trade : {}", id);
        tradeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trades?query=:query} : search for the trade corresponding
     * to the query.
     *
     * @param query the query of the trade search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/trades")
    public ResponseEntity<List<Trade>> searchTrades(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Trades for query {}", query);
        Page<Trade> page = tradeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code PUT  /trades/:id/rate} : Finalises a trade and/or listing by providing rating and confirmation.
     *
     * @param id the id of the trade to rate.
     * @param tradeConfirmDTO contains rating information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trade,
     * or with status {@code 400 (Bad Request)} if the trade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trades/{id}/rate")
    public ResponseEntity<Rating> rateTrade(@PathVariable Long id, @RequestBody TradeConfirmDTO tradeConfirmDTO) throws URISyntaxException {
        log.debug("REST request to rate Trade with ID: {}", id);
        Optional<Trade> tradeOptional = tradeService.findOne(id);
        if (!tradeOptional.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trade trade = tradeOptional.get();
        Optional<User> owner = this.userService.getUserWithAuthorities();
        if (!owner.isPresent()) {
            throw new BadRequestAlertException("You must create an entity as a logged user", ENTITY_NAME, "notloggeduser");
        }
        User recipient = owner.get().equals(trade.getOffer().getOwner()) ? trade.getOffer().getListing().getOwner() : trade.getOffer().getOwner();

        Rating rating = new Rating();
        rating.setMessage(tradeConfirmDTO.getMessage());
        rating.setScore(tradeConfirmDTO.getScore());
        rating.setTimestamp(Instant.now());
        rating.setOwner(owner.get());
        rating.setRecipient(recipient);
        if (owner.get().equals(trade.getOffer().getOwner())) {
            trade.setOfferOwnerStatus(tradeConfirmDTO.getTradeStatus());
        } else {
            trade.setListingOwnerStatus(tradeConfirmDTO.getTradeStatus());
        }
        Trade tradeEntity = tradeService.save(trade);
        Rating result = ratingService.save(rating);
        if (tradeEntity.getListingOwnerStatus().equals(TradeStatus.CONFIRMED) && tradeEntity.getOfferOwnerStatus().equals(TradeStatus.CONFIRMED)) {
            Listing listing = trade.getOffer().getListing();
            listing.setActive(false);
            listingService.save(listing);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trade.getId().toString()))
            .body(result);
    }

}
