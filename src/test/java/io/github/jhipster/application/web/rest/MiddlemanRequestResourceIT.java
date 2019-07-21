package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RsnsalesApp;
import io.github.jhipster.application.domain.MiddlemanRequest;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.repository.MiddlemanRequestRepository;
import io.github.jhipster.application.repository.search.MiddlemanRequestSearchRepository;
import io.github.jhipster.application.service.MiddlemanRequestService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link MiddlemanRequestResource} REST controller.
 */
@SpringBootTest(classes = RsnsalesApp.class)
public class MiddlemanRequestResourceIT {

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MiddlemanRequestRepository middlemanRequestRepository;

    @Autowired
    private MiddlemanRequestService middlemanRequestService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.MiddlemanRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private MiddlemanRequestSearchRepository mockMiddlemanRequestSearchRepository;

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

    private MockMvc restMiddlemanRequestMockMvc;

    private MiddlemanRequest middlemanRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MiddlemanRequestResource middlemanRequestResource = new MiddlemanRequestResource(middlemanRequestService);
        this.restMiddlemanRequestMockMvc = MockMvcBuilders.standaloneSetup(middlemanRequestResource)
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
    public static MiddlemanRequest createEntity(EntityManager em) {
        MiddlemanRequest middlemanRequest = new MiddlemanRequest()
            .timestamp(DEFAULT_TIMESTAMP)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Trade trade;
        if (TestUtil.findAll(em, Trade.class).isEmpty()) {
            trade = TradeResourceIT.createEntity(em);
            em.persist(trade);
            em.flush();
        } else {
            trade = TestUtil.findAll(em, Trade.class).get(0);
        }
        middlemanRequest.setTrade(trade);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        middlemanRequest.setOwner(user);
        // Add required entity
        middlemanRequest.setRecipient(user);
        return middlemanRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiddlemanRequest createUpdatedEntity(EntityManager em) {
        MiddlemanRequest middlemanRequest = new MiddlemanRequest()
            .timestamp(UPDATED_TIMESTAMP)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Trade trade;
        if (TestUtil.findAll(em, Trade.class).isEmpty()) {
            trade = TradeResourceIT.createUpdatedEntity(em);
            em.persist(trade);
            em.flush();
        } else {
            trade = TestUtil.findAll(em, Trade.class).get(0);
        }
        middlemanRequest.setTrade(trade);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        middlemanRequest.setOwner(user);
        // Add required entity
        middlemanRequest.setRecipient(user);
        return middlemanRequest;
    }

    @BeforeEach
    public void initTest() {
        middlemanRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createMiddlemanRequest() throws Exception {
        int databaseSizeBeforeCreate = middlemanRequestRepository.findAll().size();

        // Create the MiddlemanRequest
        restMiddlemanRequestMockMvc.perform(post("/api/middleman-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(middlemanRequest)))
            .andExpect(status().isCreated());

        // Validate the MiddlemanRequest in the database
        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeCreate + 1);
        MiddlemanRequest testMiddlemanRequest = middlemanRequestList.get(middlemanRequestList.size() - 1);
        assertThat(testMiddlemanRequest.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testMiddlemanRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the MiddlemanRequest in Elasticsearch
        verify(mockMiddlemanRequestSearchRepository, times(1)).save(testMiddlemanRequest);
    }

    @Test
    @Transactional
    public void createMiddlemanRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = middlemanRequestRepository.findAll().size();

        // Create the MiddlemanRequest with an existing ID
        middlemanRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiddlemanRequestMockMvc.perform(post("/api/middleman-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(middlemanRequest)))
            .andExpect(status().isBadRequest());

        // Validate the MiddlemanRequest in the database
        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the MiddlemanRequest in Elasticsearch
        verify(mockMiddlemanRequestSearchRepository, times(0)).save(middlemanRequest);
    }


    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = middlemanRequestRepository.findAll().size();
        // set the field null
        middlemanRequest.setTimestamp(null);

        // Create the MiddlemanRequest, which fails.

        restMiddlemanRequestMockMvc.perform(post("/api/middleman-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(middlemanRequest)))
            .andExpect(status().isBadRequest());

        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMiddlemanRequests() throws Exception {
        // Initialize the database
        middlemanRequestRepository.saveAndFlush(middlemanRequest);

        // Get all the middlemanRequestList
        restMiddlemanRequestMockMvc.perform(get("/api/middleman-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(middlemanRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getMiddlemanRequest() throws Exception {
        // Initialize the database
        middlemanRequestRepository.saveAndFlush(middlemanRequest);

        // Get the middlemanRequest
        restMiddlemanRequestMockMvc.perform(get("/api/middleman-requests/{id}", middlemanRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(middlemanRequest.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMiddlemanRequest() throws Exception {
        // Get the middlemanRequest
        restMiddlemanRequestMockMvc.perform(get("/api/middleman-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiddlemanRequest() throws Exception {
        // Initialize the database
        middlemanRequestService.save(middlemanRequest);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMiddlemanRequestSearchRepository);

        int databaseSizeBeforeUpdate = middlemanRequestRepository.findAll().size();

        // Update the middlemanRequest
        MiddlemanRequest updatedMiddlemanRequest = middlemanRequestRepository.findById(middlemanRequest.getId()).get();
        // Disconnect from session so that the updates on updatedMiddlemanRequest are not directly saved in db
        em.detach(updatedMiddlemanRequest);
        updatedMiddlemanRequest
            .timestamp(UPDATED_TIMESTAMP)
            .description(UPDATED_DESCRIPTION);

        restMiddlemanRequestMockMvc.perform(put("/api/middleman-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMiddlemanRequest)))
            .andExpect(status().isOk());

        // Validate the MiddlemanRequest in the database
        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeUpdate);
        MiddlemanRequest testMiddlemanRequest = middlemanRequestList.get(middlemanRequestList.size() - 1);
        assertThat(testMiddlemanRequest.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMiddlemanRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the MiddlemanRequest in Elasticsearch
        verify(mockMiddlemanRequestSearchRepository, times(1)).save(testMiddlemanRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingMiddlemanRequest() throws Exception {
        int databaseSizeBeforeUpdate = middlemanRequestRepository.findAll().size();

        // Create the MiddlemanRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiddlemanRequestMockMvc.perform(put("/api/middleman-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(middlemanRequest)))
            .andExpect(status().isBadRequest());

        // Validate the MiddlemanRequest in the database
        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MiddlemanRequest in Elasticsearch
        verify(mockMiddlemanRequestSearchRepository, times(0)).save(middlemanRequest);
    }

    @Test
    @Transactional
    public void deleteMiddlemanRequest() throws Exception {
        // Initialize the database
        middlemanRequestService.save(middlemanRequest);

        int databaseSizeBeforeDelete = middlemanRequestRepository.findAll().size();

        // Delete the middlemanRequest
        restMiddlemanRequestMockMvc.perform(delete("/api/middleman-requests/{id}", middlemanRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MiddlemanRequest> middlemanRequestList = middlemanRequestRepository.findAll();
        assertThat(middlemanRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MiddlemanRequest in Elasticsearch
        verify(mockMiddlemanRequestSearchRepository, times(1)).deleteById(middlemanRequest.getId());
    }

    @Test
    @Transactional
    public void searchMiddlemanRequest() throws Exception {
        // Initialize the database
        middlemanRequestService.save(middlemanRequest);
        when(mockMiddlemanRequestSearchRepository.search(queryStringQuery("id:" + middlemanRequest.getId())))
            .thenReturn(Collections.singletonList(middlemanRequest));
        // Search the middlemanRequest
        restMiddlemanRequestMockMvc.perform(get("/api/_search/middleman-requests?query=id:" + middlemanRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(middlemanRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiddlemanRequest.class);
        MiddlemanRequest middlemanRequest1 = new MiddlemanRequest();
        middlemanRequest1.setId(1L);
        MiddlemanRequest middlemanRequest2 = new MiddlemanRequest();
        middlemanRequest2.setId(middlemanRequest1.getId());
        assertThat(middlemanRequest1).isEqualTo(middlemanRequest2);
        middlemanRequest2.setId(2L);
        assertThat(middlemanRequest1).isNotEqualTo(middlemanRequest2);
        middlemanRequest1.setId(null);
        assertThat(middlemanRequest1).isNotEqualTo(middlemanRequest2);
    }
}
