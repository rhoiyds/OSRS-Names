package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RsnsalesApp;
import io.github.jhipster.application.domain.Rating;
import io.github.jhipster.application.domain.User;
import io.github.jhipster.application.domain.Trade;
import io.github.jhipster.application.repository.RatingRepository;
import io.github.jhipster.application.repository.search.RatingSearchRepository;
import io.github.jhipster.application.service.RatingService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.RatingCriteria;
import io.github.jhipster.application.service.RatingQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
 * Integration tests for the {@Link RatingResource} REST controller.
 */
@SpringBootTest(classes = RsnsalesApp.class)
public class RatingResourceIT {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RatingSearchRepositoryMockConfiguration
     */
    @Autowired
    private RatingSearchRepository mockRatingSearchRepository;

    @Autowired
    private RatingQueryService ratingQueryService;

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

    private MockMvc restRatingMockMvc;

    private Rating rating;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RatingResource ratingResource = new RatingResource(ratingService, ratingQueryService);
        this.restRatingMockMvc = MockMvcBuilders.standaloneSetup(ratingResource)
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
    public static Rating createEntity(EntityManager em) {
        Rating rating = new Rating()
            .score(DEFAULT_SCORE)
            .message(DEFAULT_MESSAGE)
            .timestamp(DEFAULT_TIMESTAMP);
        return rating;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createUpdatedEntity(EntityManager em) {
        Rating rating = new Rating()
            .score(UPDATED_SCORE)
            .message(UPDATED_MESSAGE)
            .timestamp(UPDATED_TIMESTAMP);
        return rating;
    }

    @BeforeEach
    public void initTest() {
        rating = createEntity(em);
    }

    @Test
    @Transactional
    public void createRating() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();

        // Create the Rating
        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isCreated());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate + 1);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testRating.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testRating.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Rating in Elasticsearch
        verify(mockRatingSearchRepository, times(1)).save(testRating);
    }

    @Test
    @Transactional
    public void createRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();

        // Create the Rating with an existing ID
        rating.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rating in Elasticsearch
        verify(mockRatingSearchRepository, times(0)).save(rating);
    }


    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingRepository.findAll().size();
        // set the field null
        rating.setScore(null);

        // Create the Rating, which fails.

        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isBadRequest());

        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingRepository.findAll().size();
        // set the field null
        rating.setMessage(null);

        // Create the Rating, which fails.

        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isBadRequest());

        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRatings() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }
    
    @Test
    @Transactional
    public void getRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get the rating
        restRatingMockMvc.perform(get("/api/ratings/{id}", rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rating.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    public void getAllRatingsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where score equals to DEFAULT_SCORE
        defaultRatingShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the ratingList where score equals to UPDATED_SCORE
        defaultRatingShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllRatingsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultRatingShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the ratingList where score equals to UPDATED_SCORE
        defaultRatingShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllRatingsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where score is not null
        defaultRatingShouldBeFound("score.specified=true");

        // Get all the ratingList where score is null
        defaultRatingShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatingsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where score greater than or equals to DEFAULT_SCORE
        defaultRatingShouldBeFound("score.greaterOrEqualThan=" + DEFAULT_SCORE);

        // Get all the ratingList where score greater than or equals to (DEFAULT_SCORE + 1)
        defaultRatingShouldNotBeFound("score.greaterOrEqualThan=" + (DEFAULT_SCORE + 1));
    }

    @Test
    @Transactional
    public void getAllRatingsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where score less than or equals to DEFAULT_SCORE
        defaultRatingShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the ratingList where score less than or equals to (DEFAULT_SCORE + 1)
        defaultRatingShouldBeFound("score.lessThan=" + (DEFAULT_SCORE + 1));
    }


    @Test
    @Transactional
    public void getAllRatingsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where message equals to DEFAULT_MESSAGE
        defaultRatingShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the ratingList where message equals to UPDATED_MESSAGE
        defaultRatingShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllRatingsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultRatingShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the ratingList where message equals to UPDATED_MESSAGE
        defaultRatingShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllRatingsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where message is not null
        defaultRatingShouldBeFound("message.specified=true");

        // Get all the ratingList where message is null
        defaultRatingShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatingsByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where timestamp equals to DEFAULT_TIMESTAMP
        defaultRatingShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the ratingList where timestamp equals to UPDATED_TIMESTAMP
        defaultRatingShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllRatingsByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultRatingShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the ratingList where timestamp equals to UPDATED_TIMESTAMP
        defaultRatingShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllRatingsByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where timestamp is not null
        defaultRatingShouldBeFound("timestamp.specified=true");

        // Get all the ratingList where timestamp is null
        defaultRatingShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatingsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        rating.setOwner(owner);
        ratingRepository.saveAndFlush(rating);
        Long ownerId = owner.getId();

        // Get all the ratingList where owner equals to ownerId
        defaultRatingShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the ratingList where owner equals to ownerId + 1
        defaultRatingShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllRatingsByRecipientIsEqualToSomething() throws Exception {
        // Initialize the database
        User recipient = UserResourceIT.createEntity(em);
        em.persist(recipient);
        em.flush();
        rating.setRecipient(recipient);
        ratingRepository.saveAndFlush(rating);
        Long recipientId = recipient.getId();

        // Get all the ratingList where recipient equals to recipientId
        defaultRatingShouldBeFound("recipientId.equals=" + recipientId);

        // Get all the ratingList where recipient equals to recipientId + 1
        defaultRatingShouldNotBeFound("recipientId.equals=" + (recipientId + 1));
    }


    @Test
    @Transactional
    public void getAllRatingsByTradeIsEqualToSomething() throws Exception {
        // Initialize the database
        Trade trade = TradeResourceIT.createEntity(em);
        em.persist(trade);
        em.flush();
        rating.setTrade(trade);
        ratingRepository.saveAndFlush(rating);
        Long tradeId = trade.getId();

        // Get all the ratingList where trade equals to tradeId
        defaultRatingShouldBeFound("tradeId.equals=" + tradeId);

        // Get all the ratingList where trade equals to tradeId + 1
        defaultRatingShouldNotBeFound("tradeId.equals=" + (tradeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingShouldBeFound(String filter) throws Exception {
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));

        // Check, that the count call also returns 1
        restRatingMockMvc.perform(get("/api/ratings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingShouldNotBeFound(String filter) throws Exception {
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingMockMvc.perform(get("/api/ratings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRating() throws Exception {
        // Get the rating
        restRatingMockMvc.perform(get("/api/ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRating() throws Exception {
        // Initialize the database
        ratingService.save(rating);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRatingSearchRepository);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating
        Rating updatedRating = ratingRepository.findById(rating.getId()).get();
        // Disconnect from session so that the updates on updatedRating are not directly saved in db
        em.detach(updatedRating);
        updatedRating
            .score(UPDATED_SCORE)
            .message(UPDATED_MESSAGE)
            .timestamp(UPDATED_TIMESTAMP);

        restRatingMockMvc.perform(put("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRating)))
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testRating.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testRating.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Rating in Elasticsearch
        verify(mockRatingSearchRepository, times(1)).save(testRating);
    }

    @Test
    @Transactional
    public void updateNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Create the Rating

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc.perform(put("/api/ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rating)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rating in Elasticsearch
        verify(mockRatingSearchRepository, times(0)).save(rating);
    }

    @Test
    @Transactional
    public void deleteRating() throws Exception {
        // Initialize the database
        ratingService.save(rating);

        int databaseSizeBeforeDelete = ratingRepository.findAll().size();

        // Delete the rating
        restRatingMockMvc.perform(delete("/api/ratings/{id}", rating.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rating in Elasticsearch
        verify(mockRatingSearchRepository, times(1)).deleteById(rating.getId());
    }

    @Test
    @Transactional
    public void searchRating() throws Exception {
        // Initialize the database
        ratingService.save(rating);
        when(mockRatingSearchRepository.search(queryStringQuery("id:" + rating.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rating), PageRequest.of(0, 1), 1));
        // Search the rating
        restRatingMockMvc.perform(get("/api/_search/ratings?query=id:" + rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rating.class);
        Rating rating1 = new Rating();
        rating1.setId(1L);
        Rating rating2 = new Rating();
        rating2.setId(rating1.getId());
        assertThat(rating1).isEqualTo(rating2);
        rating2.setId(2L);
        assertThat(rating1).isNotEqualTo(rating2);
        rating1.setId(null);
        assertThat(rating1).isNotEqualTo(rating2);
    }
}
