package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Listing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Listing} entity.
 */
public interface ListingSearchRepository extends ElasticsearchRepository<Listing, Long> {
}
