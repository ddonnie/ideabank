package com.dataart.fastforward.repository;

import com.dataart.fastforward.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by logariett on 22.11.2016.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
