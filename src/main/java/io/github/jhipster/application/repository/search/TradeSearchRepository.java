package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Trade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Trade} entity.
 */
public interface TradeSearchRepository extends ElasticsearchRepository<Trade, Long> {
}
