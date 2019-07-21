package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Comment} entity.
 */
public interface CommentSearchRepository extends ElasticsearchRepository<Comment, Long> {
}
