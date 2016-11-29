package com.dataart.fastforward.app.dao;


import com.dataart.fastforward.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by logariett on 19.11.2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        User findByUsername(String username);
}
