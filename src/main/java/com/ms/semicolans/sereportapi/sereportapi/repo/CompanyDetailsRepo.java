package com.ms.semicolans.sereportapi.sereportapi.repo;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyDetailsRepo extends JpaRepository<CompanyDetails, String> {

    Optional<CompanyDetails> findByUsername(String username);
    Optional<CompanyDetails> findByUsernameAndPinnumber(String username, String pinnumber);
    List<CompanyDetails> findByPinnumber(String pinnumber);   // ← changed to List
}