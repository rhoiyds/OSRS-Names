package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tag}.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tag the entity to save.
     * @return the persisted entity.
     */
    Tag save(Tag tag);

    /**
     * Get all the tags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tag> findAll(Pageable pageable);


    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tag> findOne(Long id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * If a tag exists with the name, return it, else create it.
     *
     * @param name the name of the tag to find/save.
     */
    Tag createIfNotExist(Tag tag);

    List<Tag> getTagsByName(String name);
}
