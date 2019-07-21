package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.CommentService;
import io.github.jhipster.application.domain.Comment;
import io.github.jhipster.application.repository.CommentRepository;
import io.github.jhipster.application.repository.search.CommentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentSearchRepository commentSearchRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentSearchRepository commentSearchRepository) {
        this.commentRepository = commentRepository;
        this.commentSearchRepository = commentSearchRepository;
    }

    /**
     * Save a comment.
     *
     * @param comment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Comment save(Comment comment) {
        log.debug("Request to save Comment : {}", comment);
        Comment result = commentRepository.save(comment);
        commentSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        log.debug("Request to get all Comments");
        return commentRepository.findAll();
    }


    /**
     * Get one comment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
        commentSearchRepository.deleteById(id);
    }

    /**
     * Search for the comment corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> search(String query) {
        log.debug("Request to search Comments for query {}", query);
        return StreamSupport
            .stream(commentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
