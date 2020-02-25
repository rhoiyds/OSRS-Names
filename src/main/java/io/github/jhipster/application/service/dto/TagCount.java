package io.github.jhipster.application.service.dto;

import java.io.Serializable;

import io.github.jhipster.application.domain.Tag;

public class TagCount {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long count;

    private Tag tag;

    public TagCount() {}

    public TagCount(Tag tag, Long count) {
        this.count = count;
        this.tag = tag;
    }

    public Long getCount() {
        return count;
    }

    void setCount(Long count) {
        this.count = count;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "TagCount{" +
            "tag='" + tag.toString() + '\'' +
            ", count='" + count + '\'' +
            "}";
    }

}