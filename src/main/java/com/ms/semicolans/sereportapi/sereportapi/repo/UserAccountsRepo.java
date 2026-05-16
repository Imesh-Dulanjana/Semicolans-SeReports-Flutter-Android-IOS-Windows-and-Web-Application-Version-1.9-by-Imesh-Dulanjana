package com.ms.semicolans.sereportapi.sereportapi.repo;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountsRepo extends JpaRepository<UserAccounts, Long> {

    // Usernames are not unique – return List to avoid NonUniqueResultException
    List<UserAccounts> findByUserName(String userName);

    List<UserAccounts> findByUserNameAndPinnumber(String userName, String pinnumber);

    // For full credential check (username + password + pinnumber)
    List<UserAccounts> findByUserNameAndUserPasswordAndPinnumber(
            String userName, String userPassword, String pinnumber);
}