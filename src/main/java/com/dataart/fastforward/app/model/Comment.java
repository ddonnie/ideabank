package com.dataart.fastforward.app.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by logariett on 22.11.2016.
 */

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "comment_id", length = 6, nullable = false)
    private long commentId;

    @ManyToOne//(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

/*    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;*/

    @Column(name = "idea_id")
    private long ideaId;

    @Column(name = "comment_text")
    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    public Comment() {}

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    /*    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }*/

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
