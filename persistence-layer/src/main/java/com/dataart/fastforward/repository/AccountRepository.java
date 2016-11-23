package com.dataart.fastforward.repository;

import com.dataart.fastforward.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by logariett on 19.11.2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
