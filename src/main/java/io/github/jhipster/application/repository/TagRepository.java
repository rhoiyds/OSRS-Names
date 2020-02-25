package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query("select tag from Tag tag where lower(tag.name) = lower(:name)")
    Optional<Tag> findByName(@Param("name") String name);

    List<Tag> findByNameContainsIgnoreCase(String name);

}
