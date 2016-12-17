package com.dataart.fastforward.app.model;

/**
 * Created by logariett on 22.11.2016.
 */


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User_mark")
public class Mark {

    @EmbeddedId
    private MarkPk markId;

    @Column(name="mark")
    private int mark;

    public Mark() {
    }

    public MarkPk getMarkId() {
        return markId;
    }

    public void setMarkId(MarkPk markId) {
        this.markId = markId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Embeddable
//    @Access (AccessType.FIELD)
    public static class MarkPk implements Serializable {
        @ManyToOne
        @JoinColumn(name = "idea_id", nullable = false)
        private Idea idea;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        public MarkPk() {
        }

        public MarkPk(Idea idea, User user) {
            this.idea = idea;
            this.user = user;
        }

        public Idea getIdea() {
            return idea;
        }

        public void setIdea(Idea idea) {
            this.idea = idea;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}


