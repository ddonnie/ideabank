package com.dataart.fastforward.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by logariett on 22.11.2016.
 */
@Entity
@Table(name = "Tags")
public class Tag {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "tag_id", length = 6, nullable = false)
    private long tagId;

    @Column(name = "tag_text")
    private String tag_name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<Idea> ideas;

    public Tag() {}

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public Set<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(Set<Idea> ideas) {
        this.ideas = ideas;
    }
}
