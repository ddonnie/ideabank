package com.dataart.fastforward.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

/*    @Column(name = "idea_id")
    private long ideaId;*/

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

/*    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }*/

        public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (commentId != comment.commentId) return false;
        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        if (idea != null ? !idea.equals(comment.idea) : comment.idea != null) return false;
        if (commentText != null ? !commentText.equals(comment.commentText) : comment.commentText != null) return false;
        return creationDate != null ? creationDate.equals(comment.creationDate) : comment.creationDate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (commentId ^ (commentId >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (idea != null ? idea.hashCode() : 0);
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", authorId=" + author.getUserId() +
                ", ideaId=" + idea.getIdeaId() +
                ", commentText='" + commentText + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
