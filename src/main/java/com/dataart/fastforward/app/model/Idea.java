package com.dataart.fastforward.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @Column(name = "idea_name")
    private String ideaName;

    @Column(name = "idea_text")
    private String ideaText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @OneToMany(mappedBy = "idea", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

/*    @OneToMany(mappedBy = "idea", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Attachment> attachments = new HashSet<>();*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Bookmarks",
            joinColumns = @JoinColumn(name = "idea_id", referencedColumnName = "idea_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )

    @JsonBackReference
    private Set<User> usersWhoBookmarked = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Ideas_Tags",
            joinColumns = @JoinColumn(name="idea_id", referencedColumnName="idea_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id", referencedColumnName="tag_id")
    )
    @JsonBackReference
    private Set<Tag> tags = new HashSet<>();

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

    public String getIdeaName() {
        return ideaName;
    }

    public void setIdeaName(String ideName) {
        this.ideaName = ideName;
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

/*    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }*/

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
        if (ideaName != null ? !ideaName.equals(idea.ideaName) : idea.ideaName != null) return false;
        return creationDate != null ? creationDate.equals(idea.creationDate) : idea.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (ideaId ^ (ideaId >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (ideaName != null ? ideaName.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Idea{" +
                "ideaId=" + ideaId +
                ", author=" + author +
                ", ideaName='" + ideaName + '\'' +
                ", ideaText='" + ideaText + '\'' +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
