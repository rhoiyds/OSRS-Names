package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Tag;
import io.github.jhipster.application.service.TagService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.TagCriteria;
import io.github.jhipster.application.service.ListingService;
import io.github.jhipster.application.service.TagQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing
 * {@link io.github.jhipster.application.domain.Tag}.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    private static final String ENTITY_NAME = "tag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagService tagService;

    private final TagQueryService tagQueryService;

    private final ListingService listingService;

    public TagResource(TagService tagService, TagQueryService tagQueryService, ListingService listingService) {
        this.tagService = tagService;
        this.tagQueryService = tagQueryService;
        this.listingService = listingService;
    }

    /**
     * {@code POST  /tags} : Create a new tag.
     *
     * @param tag the tag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new tag, or with status {@code 400 (Bad Request)} if the tag
     *         has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        log.debug("REST request to save Tag : {}", tag);
        if (tag.getId() != null) {
            throw new BadRequestAlertException("A new tag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tag result = tagService.save(tag);
        return ResponseEntity
                .created(new URI("/api/tags/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /tags} : Updates an existing tag.
     *
     * @param tag the tag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated tag, or with status {@code 400 (Bad Request)} if the tag
     *         is not valid, or with status {@code 500 (Internal Server Error)} if
     *         the tag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tags")
    public ResponseEntity<Tag> updateTag(@Valid @RequestBody Tag tag) throws URISyntaxException {
        log.debug("REST request to update Tag : {}", tag);
        if (tag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tag result = tagService.save(tag);
        return ResponseEntity.ok()
                .headers(
                        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tag.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /tags} : get all the tags.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @param criteria    the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of tags in body.
     */
    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags(TagCriteria criteria, Pageable pageable,
            @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Tags by criteria: {}", criteria);
        Page<Tag> page = tagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tags/count} : count all the tags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/tags/count")
    public ResponseEntity<Long> countTags(TagCriteria criteria) {
        log.debug("REST request to count Tags by criteria: {}", criteria);
        return ResponseEntity.ok().body(tagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tags/:id} : get the "id" tag.
     *
     * @param id the id of the tag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the tag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        log.debug("REST request to get Tag : {}", id);
        Optional<Tag> tag = tagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tag);
    }

    /**
     * {@code DELETE  /tags/:id} : delete the "id" tag.
     *
     * @param id the id of the tag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code GET  /tags/search} : get the total numbers.
     *
     * @param name the name of the tag a user is searching for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the tag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tags/search")
    public ResponseEntity<List<ImmutablePair<Tag, Long>>> getTagsUsed(@RequestParam String name) {
        log.debug("REST request to get Tags by name : {}", name);
        List<Tag> tags = tagService.getTagsByName(name);
        log.debug("REST request to get Tags by name list be like : {}", tags);
        tags.stream().map(tag -> new ImmutablePair<>(tag, listingService.countByTag(tag))).collect(Collectors.toList());
        return ResponseEntity.ok().body(tags.stream().map(tag -> new ImmutablePair<>(tag, listingService.countByTag(tag))).collect(Collectors.toList()));
    }
}
