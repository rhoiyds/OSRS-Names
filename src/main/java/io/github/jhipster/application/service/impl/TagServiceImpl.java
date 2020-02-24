package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TagService;
import io.github.jhipster.application.domain.Tag;
import io.github.jhipster.application.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Tag}.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Save a tag.
     *
     * @param tag the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Tag save(Tag tag) {
        log.debug("Request to save Tag : {}", tag);
        return tagRepository.save(tag);
    }

    /**
     * Get all the tags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tag> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagRepository.findAll(pageable);
    }


    /**
     * Get one tag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> findOne(Long id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id);
    }

    /**
     * Delete the tag by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }

    @Override
    public Tag createIfNotExist(Tag tag) {
        log.debug("Request to find or create Tag : {}", tag.getName());
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        return existingTag.isPresent() ? existingTag.get() : this.save(tag);
    }

    @Override
    public List<Tag> getTagsByName(String name) {
        return tagRepository.findByNameLikeIgnoreCase(name);
    }
}
