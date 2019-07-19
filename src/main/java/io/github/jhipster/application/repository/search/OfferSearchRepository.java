package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Offer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Offer} entity.
 */
public interface OfferSearchRepository extends ElasticsearchRepository<Offer, Long> {
}
