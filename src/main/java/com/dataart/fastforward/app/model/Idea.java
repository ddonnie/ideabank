package com.dataart.fastforward.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by logariett on 22.11.2016.
 */

@Entity
@Table(name = "Ideas")
public class Idea {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "idea_id", length = 6, nullable = false)
    private long ideaId;

    @ManyToOne//(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "idea_text")
    private String ideaText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookmarkedIdeas",fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<User> usersWhoBookmarked;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "ideasWithThisTag")
    @JsonManagedReference
    private Set<Tag> tags;

    public Idea() {}

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Set<User> getUsersWhoBookmarked() {
        return usersWhoBookmarked;
    }

    public void setUsersWhoBookmarked(Set<User> users) {
        this.usersWhoBookmarked = users;
    }

    @JsonIgnore
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        if (ideaId != idea.ideaId) return false;
        if (author != null ? !author.equals(idea.author) : idea.author != null) return false;
        if (ideaText != null ? !ideaText.equals(idea.ideaText) : idea.ideaText != null) return false;
        return creationDate != null ? creationDate.equals(idea.creationDate) : idea.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (ideaId ^ (ideaId >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (ideaText != null ? ideaText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Idea{" +
                "ideaId=" + ideaId +
                ", author=" + author +
                ", ideaText='" + ideaText + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
