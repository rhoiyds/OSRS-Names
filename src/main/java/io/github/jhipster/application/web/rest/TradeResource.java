package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.OfferRepository;
import io.github.jhipster.application.repository.search.TradeSearchRepository;
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
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Trade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TradeResource {

    private final Logger log = LoggerFactory.getLogger(TradeResource.class);

    private static final String ENTITY_NAME = "trade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeRepository tradeRepository;

    private final TradeSearchRepository tradeSearchRepository;

    private final OfferRepository offerRepository;

    public TradeResource(TradeRepository tradeRepository, TradeSearchRepository tradeSearchRepository, OfferRepository offerRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchRepository = tradeSearchRepository;
        this.offerRepository = offerRepository;
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
        Long offerId = trade.getOffer().getId();
        offerRepository.findById(offerId).ifPresent(trade::offer);
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
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
        Trade result = tradeRepository.save(trade);
        tradeSearchRepository.save(result);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trades in body.
     */
    @GetMapping("/trades")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Trade>> getAllTrades(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Trades");
        Page<Trade> page = tradeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trades/:id} : get the "id" trade.
     *
     * @param id the id of the trade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trades/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Trade> getTrade(@PathVariable Long id) {
        log.debug("REST request to get Trade : {}", id);
        Optional<Trade> trade = tradeRepository.findById(id);
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
        tradeRepository.deleteById(id);
        tradeSearchRepository.deleteById(id);
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
        Page<Trade> page = tradeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
