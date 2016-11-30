package com.dataart.fastforward.app.model;

import com.dataart.fastforward.app.model.Idea;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by logariett on 22.11.2016.
 */

@Entity
@Table(name = "Tags")
public class Tag implements Comparable<Tag> {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "tag_id", length = 6, nullable = false)
    private long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Ideas_Tags",
            joinColumns = @JoinColumn(name="tag_id", referencedColumnName="tag_id"),
            inverseJoinColumns = @JoinColumn(name="idea_id", referencedColumnName="idea_id")
    )
    @JsonBackReference
    private Set<Idea> ideasWithThisTag = new TreeSet<>();

    public Tag() {}

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonIgnore
    public Set<Idea> getIdeasWithThisTag() {
        return ideasWithThisTag;
    }

    public void setIdeasWithThisTag(Set<Idea> ideasWithThisTag) {
        this.ideasWithThisTag = ideasWithThisTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (tagId != tag.tagId) return false;
        return tagName != null ? tagName.equals(tag.tagName) : tag.tagName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (tagId ^ (tagId >>> 32));
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName +
                '}';
    }

    @Override
    public int compareTo(Tag o) {
        return Long.compare(o.tagId, this.tagId);
    }
}
