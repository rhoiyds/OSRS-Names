package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.OsrsnamesApp;
import io.github.jhipster.application.domain.Listing;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.Tag;
import io.github.jhipster.application.repository.ListingRepository;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.UserService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ListingCriteria;
import io.github.jhipster.application.service.ListingQueryService;

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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.ListingType;
/**
 * Integration tests for the {@Link ListingResource} REST controller.
 */
@SpringBootTest(classes = OsrsnamesApp.class)
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

    @Mock
    private UserService userServiceMock;

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingQueryService listingQueryService;

    @Autowired
    private UserService userService;

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
        final ListingResource listingResource = new ListingResource(listingService, userService, listingQueryService);
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
        ListingResource listingResource = new ListingResource(listingServiceMock, userServiceMock, listingQueryService);
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
        ListingResource listingResource = new ListingResource(listingServiceMock, userServiceMock, listingQueryService);
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
    public void getAllListingsByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where timestamp equals to DEFAULT_TIMESTAMP
        defaultListingShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the listingList where timestamp equals to UPDATED_TIMESTAMP
        defaultListingShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllListingsByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultListingShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the listingList where timestamp equals to UPDATED_TIMESTAMP
        defaultListingShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllListingsByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where timestamp is not null
        defaultListingShouldBeFound("timestamp.specified=true");

        // Get all the listingList where timestamp is null
        defaultListingShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where type equals to DEFAULT_TYPE
        defaultListingShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the listingList where type equals to UPDATED_TYPE
        defaultListingShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllListingsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultListingShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the listingList where type equals to UPDATED_TYPE
        defaultListingShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllListingsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where type is not null
        defaultListingShouldBeFound("type.specified=true");

        // Get all the listingList where type is null
        defaultListingShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByRsnIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where rsn equals to DEFAULT_RSN
        defaultListingShouldBeFound("rsn.equals=" + DEFAULT_RSN);

        // Get all the listingList where rsn equals to UPDATED_RSN
        defaultListingShouldNotBeFound("rsn.equals=" + UPDATED_RSN);
    }

    @Test
    @Transactional
    public void getAllListingsByRsnIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where rsn in DEFAULT_RSN or UPDATED_RSN
        defaultListingShouldBeFound("rsn.in=" + DEFAULT_RSN + "," + UPDATED_RSN);

        // Get all the listingList where rsn equals to UPDATED_RSN
        defaultListingShouldNotBeFound("rsn.in=" + UPDATED_RSN);
    }

    @Test
    @Transactional
    public void getAllListingsByRsnIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where rsn is not null
        defaultListingShouldBeFound("rsn.specified=true");

        // Get all the listingList where rsn is null
        defaultListingShouldNotBeFound("rsn.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where amount equals to DEFAULT_AMOUNT
        defaultListingShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the listingList where amount equals to UPDATED_AMOUNT
        defaultListingShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllListingsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultListingShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the listingList where amount equals to UPDATED_AMOUNT
        defaultListingShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllListingsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where amount is not null
        defaultListingShouldBeFound("amount.specified=true");

        // Get all the listingList where amount is null
        defaultListingShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where amount greater than or equals to DEFAULT_AMOUNT
        defaultListingShouldBeFound("amount.greaterOrEqualThan=" + DEFAULT_AMOUNT);

        // Get all the listingList where amount greater than or equals to UPDATED_AMOUNT
        defaultListingShouldNotBeFound("amount.greaterOrEqualThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllListingsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where amount less than or equals to DEFAULT_AMOUNT
        defaultListingShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the listingList where amount less than or equals to UPDATED_AMOUNT
        defaultListingShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllListingsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where description equals to DEFAULT_DESCRIPTION
        defaultListingShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the listingList where description equals to UPDATED_DESCRIPTION
        defaultListingShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllListingsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultListingShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the listingList where description equals to UPDATED_DESCRIPTION
        defaultListingShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllListingsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where description is not null
        defaultListingShouldBeFound("description.specified=true");

        // Get all the listingList where description is null
        defaultListingShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where active equals to DEFAULT_ACTIVE
        defaultListingShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the listingList where active equals to UPDATED_ACTIVE
        defaultListingShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllListingsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultListingShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the listingList where active equals to UPDATED_ACTIVE
        defaultListingShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllListingsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingRepository.saveAndFlush(listing);

        // Get all the listingList where active is not null
        defaultListingShouldBeFound("active.specified=true");

        // Get all the listingList where active is null
        defaultListingShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByOwnerIsEqualToSomething() throws Exception {
        // Get already existing entity
        User owner = listing.getOwner();
        listingRepository.saveAndFlush(listing);
        Long ownerId = owner.getId();

        // Get all the listingList where owner equals to ownerId
        defaultListingShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the listingList where owner equals to ownerId + 1
        defaultListingShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllListingsByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        Tag tags = TagResourceIT.createEntity(em);
        em.persist(tags);
        em.flush();
        listing.addTags(tags);
        listingRepository.saveAndFlush(listing);
        Long tagsId = tags.getId();

        // Get all the listingList where tags equals to tagsId
        defaultListingShouldBeFound("tagsId.equals=" + tagsId);

        // Get all the listingList where tags equals to tagsId + 1
        defaultListingShouldNotBeFound("tagsId.equals=" + (tagsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultListingShouldBeFound(String filter) throws Exception {
        restListingMockMvc.perform(get("/api/listings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listing.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rsn").value(hasItem(DEFAULT_RSN)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restListingMockMvc.perform(get("/api/listings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultListingShouldNotBeFound(String filter) throws Exception {
        restListingMockMvc.perform(get("/api/listings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restListingMockMvc.perform(get("/api/listings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
