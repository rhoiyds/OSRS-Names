package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.MiddlemanRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MiddlemanRequest} entity.
 */
public interface MiddlemanRequestSearchRepository extends ElasticsearchRepository<MiddlemanRequest, Long> {
}
