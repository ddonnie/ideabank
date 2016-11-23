package com.dataart.fastforward.repository;

import com.dataart.fastforward.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by logariett on 22.11.2016.
 */
@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
