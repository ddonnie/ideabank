package com.dataart.fastforward.app.dao;

import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Mark;
import com.dataart.fastforward.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by logariett on 22.11.2016.
 */
@Repository
public interface MarkRepository extends JpaRepository<Mark, Mark.MarkPk> {
    Mark findByMarkIdIdeaAndMarkIdUser(Idea idea, User user);

    List<Mark> findByMarkIdIdea(Idea idea);
    List<Mark> findByMarkIdIdea_Idea(long ideaId);

    List<Mark> findByMarkIdUser(User user);
    List<Mark> findByMarkIdUser_User(long userId);
}
