package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RsnsalesApp;
import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.repository.ListingRepository;
import io.github.jhipster.application.repository.search.ListingSearchRepository;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.ListingType;
/**
 * Integration tests for the {@Link ListingResource} REST controller.
 */
@SpringBootTest(classes = RsnsalesApp.class)
public class ListingResourceIT {

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ListingType DEFAULT_TYPE = ListingType.WANT;
    private static final ListingType UPDATED_TYPE = ListingType.HAVE;

    private static final String DEFAULT_RSN = "AAAAAAAAAA";
    private static final String UPDATED_RSN = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ListingRepository listingRepository;

    @Mock
    private ListingRepository listingRepositoryMock;

    @Mock
    private ListingService listingServiceMock;

    @Autowired
    private ListingService listingService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ListingSearchRepositoryMockConfiguration
     */
    @Autowired
    private ListingSearchRepository mockListingSearchRepository;

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

    private MockMvc restListingMockMvc;

    private Listing listing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListingResource listingResource = new ListingResource(listingService);
        this.restListingMockMvc = MockMvcBuilders.standaloneSetup(listingResource)
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
    public static Listing createEntity(EntityManager em) {
        Listing listing = new Listing()
            .timestamp(DEFAULT_TIMESTAMP)
            .type(DEFAULT_TYPE)
            .rsn(DEFAULT_RSN)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        listing.setOwner(user);
        return listing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listing createUpdatedEntity(EntityManager em) {
        Listing listing = new Listing()
            .timestamp(UPDATED_TIMESTAMP)
            .type(UPDATED_TYPE)
            .rsn(UPDATED_RSN)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        listing.setOwner(user);
        return listing;
    }

    @BeforeEach
    public void initTest() {
        listing = createEntity(em);
    }

    @Test
    @Transactional
    public void createListing() throws Exception {
        int databaseSizeBeforeCreate = listingRepository.findAll().size();

        // Create the Listing
        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isCreated());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate + 1);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testListing.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testListing.getRsn()).isEqualTo(DEFAULT_RSN);
        assertThat(testListing.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testListing.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testListing.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Listing in Elasticsearch
        verify(mockListingSearchRepository, times(1)).save(testListing);
    }

    @Test
    @Transactional
    public void createListingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listingRepository.findAll().size();

        // Create the Listing with an existing ID
        listing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Listing in Elasticsearch
        verify(mockListingSearchRepository, times(0)).save(listing);
    }


    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setTimestamp(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setType(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRsnIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingRepository.findAll().size();
        // set the field null
        listing.setRsn(null);

        // Create the Listing, which fails.

        restListingMockMvc.perform(post("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListings() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList
        restListingMockMvc.perform(get("/api/listings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rsn").value(hasItem(DEFAULT_RSN.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllListingsWithEagerRelationshipsIsEnabled() throws Exception {
        ListingResource listingResource = new ListingResource(listingServiceMock);
        when(listingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restListingMockMvc = MockMvcBuilders.standaloneSetup(listingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restListingMockMvc.perform(get("/api/listings?eagerload=true"))
        .andExpect(status().isOk());

        verify(listingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllListingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ListingResource listingResource = new ListingResource(listingServiceMock);
            when(listingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restListingMockMvc = MockMvcBuilders.standaloneSetup(listingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restListingMockMvc.perform(get("/api/listings?eagerload=true"))
        .andExpect(status().isOk());

            verify(listingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getListing() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get the listing
        restListingMockMvc.perform(get("/api/listings/{id}", listing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listing.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rsn").value(DEFAULT_RSN.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingListing() throws Exception {
        // Get the listing
        restListingMockMvc.perform(get("/api/listings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListing() throws Exception {
        // Initialize the database
        listingService.save(listing);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockListingSearchRepository);

        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Update the listing
        Listing updatedListing = listingRepository.findById(listing.getId()).get();
        // Disconnect from session so that the updates on updatedListing are not directly saved in db
        em.detach(updatedListing);
        updatedListing
            .timestamp(UPDATED_TIMESTAMP)
            .type(UPDATED_TYPE)
            .rsn(UPDATED_RSN)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restListingMockMvc.perform(put("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListing)))
            .andExpect(status().isOk());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);
        Listing testListing = listingList.get(listingList.size() - 1);
        assertThat(testListing.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testListing.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testListing.getRsn()).isEqualTo(UPDATED_RSN);
        assertThat(testListing.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testListing.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testListing.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Listing in Elasticsearch
        verify(mockListingSearchRepository, times(1)).save(testListing);
    }

    @Test
    @Transactional
    public void updateNonExistingListing() throws Exception {
        int databaseSizeBeforeUpdate = listingRepository.findAll().size();

        // Create the Listing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingMockMvc.perform(put("/api/listings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listing)))
            .andExpect(status().isBadRequest());

        // Validate the Listing in the database
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Listing in Elasticsearch
        verify(mockListingSearchRepository, times(0)).save(listing);
    }

    @Test
    @Transactional
    public void deleteListing() throws Exception {
        // Initialize the database
        listingService.save(listing);

        int databaseSizeBeforeDelete = listingRepository.findAll().size();

        // Delete the listing
        restListingMockMvc.perform(delete("/api/listings/{id}", listing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Listing> listingList = listingRepository.findAll();
        assertThat(listingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Listing in Elasticsearch
        verify(mockListingSearchRepository, times(1)).deleteById(listing.getId());
    }

    @Test
    @Transactional
    public void searchListing() throws Exception {
        // Initialize the database
        listingService.save(listing);
        when(mockListingSearchRepository.search(queryStringQuery("id:" + listing.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(listing), PageRequest.of(0, 1), 1));
        // Search the listing
        restListingMockMvc.perform(get("/api/_search/listings?query=id:" + listing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rsn").value(hasItem(DEFAULT_RSN)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Listing.class);
        Listing listing1 = new Listing();
        listing1.setId(1L);
        Listing listing2 = new Listing();
        listing2.setId(listing1.getId());
        assertThat(listing1).isEqualTo(listing2);
        listing2.setId(2L);
        assertThat(listing1).isNotEqualTo(listing2);
        listing1.setId(null);
        assertThat(listing1).isNotEqualTo(listing2);
    }
}
