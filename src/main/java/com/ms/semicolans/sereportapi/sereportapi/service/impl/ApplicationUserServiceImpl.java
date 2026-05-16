package com.ms.semicolans.sereportapi.sereportapi.service.impl;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.CompanyDetails;
import com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts;
import com.ms.semicolans.sereportapi.sereportapi.repo.CompanyDetailsRepo;
import com.ms.semicolans.sereportapi.sereportapi.repo.UserAccountsRepo;
import com.ms.semicolans.sereportapi.sereportapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl {

    private final CompanyDetailsRepo companyDetailsRepo;
    private final UserAccountsRepo userAccountsRepo;
    private final JwtUtil jwtUtil;

    /**
     * Step 1: Validates username, password and pinnumber.
     * Because usernames are not unique, we take the first matching row.
     * If no row matches → throws UsernameNotFoundException.
     */
    public UserAccounts validateCredentials(String username, String password, String pinnumber) {
        List<UserAccounts> matches = userAccountsRepo.findByUserNameAndUserPasswordAndPinnumber(
                username, password, pinnumber);
        if (matches.isEmpty()) {
            throw new UsernameNotFoundException("Invalid Username, Password, or Pin Number.");
        }
        // Username may duplicate – take the first (the combination is considered valid)
        return matches.get(0);
    }

    /**
     * Step 2: Checks that the user has SeReportsLogin = "1" in tbl_UserAccounts.
     * Throws IllegalAccessException if access is denied.
     */
    public void validateSeReportsAccess(UserAccounts user) {
        String access = user.getSeReportsLogin();
        if (access == null || access.trim().isEmpty() || !access.trim().equals("1")) {
            throw new IllegalArgumentException("You do not have access for SeReports.");
        }
    }

    /**
     * Step 3: Checks the company's subscription expiry date.
     * Matched by the pinnumber from the UserAccounts row.
     */
    public void validateSubscriptionExpiry(String pinnumber) {
        CompanyDetails company = companyDetailsRepo.findByPinnumber(pinnumber)
                .orElseThrow(() -> new IllegalStateException("Your SeReports Subscription has expired."));
        LocalDate expiry = company.getExpiryDate();
        if (expiry == null || expiry.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Your SeReports Subscription has expired.");
        }
    }

    /**
     * After all checks pass, generates a JWT for the given username.
     */
    public String generateJwt(String username) {
        return jwtUtil.generateToken(username);
    }

    /**
     * Returns the full UserAccounts row for the authenticated user.
     * Used after login to fetch permissions. We again take the first matching username
     * (should be the same one that was validated, but we use username only).
     */
    public UserAccounts getUserAccountByUsername(String username) {
        List<UserAccounts> users = userAccountsRepo.findByUserName(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return users.get(0);
    }

    /**
     * Extracts username from JWT token.
     */
    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }
}