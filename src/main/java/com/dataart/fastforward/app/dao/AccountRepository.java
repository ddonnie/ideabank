package com.dataart.fastforward.app.dao;


import com.dataart.fastforward.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by logariett on 19.11.2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select u from Account u where u.login = :login ")
    public Account findOne(@Param("login") String login);
}
