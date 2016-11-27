package com.dataart.fastforward.app.model;

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
    private Account author;

    @Column(name = "idea_text")
    private String ideaText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookmarkedIdeas",fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Account> usersWhoBookmarked;

/*    @ManyToMany
    @JoinTable(name="Ideas_Tags",
            joinColumns = @JoinColumn(name="idea_id", referencedColumnName="idea_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id", referencedColumnName="tag_id")
    )
    private Set<Tag> tags;*/

    public Idea() {}

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
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
    public Set<Account> getUsersWhoBookmarked() {
        return usersWhoBookmarked;
    }

    public void setUsersWhoBookmarked(Set<Account> users) {
        this.usersWhoBookmarked = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        return ideaId == idea.ideaId;

    }

    @Override
    public int hashCode() {
        return (int) (ideaId ^ (ideaId >>> 32));
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

    /*    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }*/
}
