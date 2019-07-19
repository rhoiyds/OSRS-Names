package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RsnsalesApp;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.repository.TradeRepository;
import io.github.jhipster.application.repository.search.TradeSearchRepository;
import io.github.jhipster.application.service.TradeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.TradeStatusType;
/**
 * Integration tests for the {@Link TradeResource} REST controller.
 */
@SpringBootTest(classes = RsnsalesApp.class)
public class TradeResourceIT {

    private static final TradeStatusType DEFAULT_STATUS = TradeStatusType.AWAITING_MIDDLEMAN;
    private static final TradeStatusType UPDATED_STATUS = TradeStatusType.CANCELLED;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TradeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TradeSearchRepository mockTradeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTradeMockMvc;

    private Trade trade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TradeResource tradeResource = new TradeResource(tradeService);
        this.restTradeMockMvc = MockMvcBuilders.standaloneSetup(tradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trade createEntity(EntityManager em) {
        Trade trade = new Trade()
            .status(DEFAULT_STATUS);
        return trade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trade createUpdatedEntity(EntityManager em) {
        Trade trade = new Trade()
            .status(UPDATED_STATUS);
        return trade;
    }

    @BeforeEach
    public void initTest() {
        trade = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrade() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade
        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isCreated());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate + 1);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Trade in Elasticsearch
        verify(mockTradeSearchRepository, times(1)).save(testTrade);
    }

    @Test
    @Transactional
    public void createTradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradeRepository.findAll().size();

        // Create the Trade with an existing ID
        trade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Trade in Elasticsearch
        verify(mockTradeSearchRepository, times(0)).save(trade);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradeRepository.findAll().size();
        // set the field null
        trade.setStatus(null);

        // Create the Trade, which fails.

        restTradeMockMvc.perform(post("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isBadRequest());

        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrades() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get all the tradeList
        restTradeMockMvc.perform(get("/api/trades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTrade() throws Exception {
        // Initialize the database
        tradeRepository.saveAndFlush(trade);

        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trade.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrade() throws Exception {
        // Get the trade
        restTradeMockMvc.perform(get("/api/trades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrade() throws Exception {
        // Initialize the database
        tradeService.save(trade);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTradeSearchRepository);

        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Update the trade
        Trade updatedTrade = tradeRepository.findById(trade.getId()).get();
        // Disconnect from session so that the updates on updatedTrade are not directly saved in db
        em.detach(updatedTrade);
        updatedTrade
            .status(UPDATED_STATUS);

        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrade)))
            .andExpect(status().isOk());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);
        Trade testTrade = tradeList.get(tradeList.size() - 1);
        assertThat(testTrade.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Trade in Elasticsearch
        verify(mockTradeSearchRepository, times(1)).save(testTrade);
    }

    @Test
    @Transactional
    public void updateNonExistingTrade() throws Exception {
        int databaseSizeBeforeUpdate = tradeRepository.findAll().size();

        // Create the Trade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradeMockMvc.perform(put("/api/trades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trade)))
            .andExpect(status().isBadRequest());

        // Validate the Trade in the database
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Trade in Elasticsearch
        verify(mockTradeSearchRepository, times(0)).save(trade);
    }

    @Test
    @Transactional
    public void deleteTrade() throws Exception {
        // Initialize the database
        tradeService.save(trade);

        int databaseSizeBeforeDelete = tradeRepository.findAll().size();

        // Delete the trade
        restTradeMockMvc.perform(delete("/api/trades/{id}", trade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trade> tradeList = tradeRepository.findAll();
        assertThat(tradeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Trade in Elasticsearch
        verify(mockTradeSearchRepository, times(1)).deleteById(trade.getId());
    }

    @Test
    @Transactional
    public void searchTrade() throws Exception {
        // Initialize the database
        tradeService.save(trade);
        when(mockTradeSearchRepository.search(queryStringQuery("id:" + trade.getId())))
            .thenReturn(Collections.singletonList(trade));
        // Search the trade
        restTradeMockMvc.perform(get("/api/_search/trades?query=id:" + trade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trade.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trade.class);
        Trade trade1 = new Trade();
        trade1.setId(1L);
        Trade trade2 = new Trade();
        trade2.setId(trade1.getId());
        assertThat(trade1).isEqualTo(trade2);
        trade2.setId(2L);
        assertThat(trade1).isNotEqualTo(trade2);
        trade1.setId(null);
        assertThat(trade1).isNotEqualTo(trade2);
    }
}
