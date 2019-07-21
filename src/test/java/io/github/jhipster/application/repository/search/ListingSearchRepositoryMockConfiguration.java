package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ListingSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ListingSearchRepositoryMockConfiguration {

    @MockBean
    private ListingSearchRepository mockListingSearchRepository;

}
