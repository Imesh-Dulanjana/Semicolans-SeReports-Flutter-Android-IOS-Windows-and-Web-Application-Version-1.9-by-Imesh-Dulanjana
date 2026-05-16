package com.ms.semicolans.sereportapi.sereportapi.service.impl;

import static com.ms.semicolans.sereportapi.sereportapi.security.ApplicationUserRole.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.semicolans.sereportapi.sereportapi.auth.ApplicationUser;
import com.ms.semicolans.sereportapi.sereportapi.entity.main.CompanyDetails;
import com.ms.semicolans.sereportapi.sereportapi.exception.PaymentRequiredException;
import com.ms.semicolans.sereportapi.sereportapi.repo.CompanyDetailsRepo;
import com.ms.semicolans.sereportapi.sereportapi.repo.UserAccountsRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements UserDetailsService {
    private final CompanyDetailsRepo companyDetailsRepo;
    private final UserAccountsRepo userAccountsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CompanyDetails> systemUser = companyDetailsRepo.findByUserName(username);
        if (systemUser.isPresent()) {
            return buildApplicationUserForSystemUser(systemUser.get());
        } else {
            throw new UsernameNotFoundException(String.format("username %s not found", username));
        }
    }

    public UserDetails loadUserByUsernameAndPinnumber(String username, String pinnumber)
            throws UsernameNotFoundException {
        log.info("[STEP 1] Validating username={}, pinnumber={}", username, pinnumber);
        
        Optional<CompanyDetails> systemUser = companyDetailsRepo.findByUserNameAndPinnumber(username, pinnumber);
        if (systemUser.isPresent()) {
            log.info("[STEP 1] ✓ PASSED - Credentials valid");
            return buildApplicationUserForSystemUser(systemUser.get());
        } else {
            log.warn("[STEP 1] ✗ FAILED - Invalid credentials");
            throw new UsernameNotFoundException("Invalid Username, Password, or Pin Number.");
        }
    }

    public void validateSeReportsAccess(String username, String pinnumber)
            throws IllegalAccessException {
        log.info("[STEP 2] Validating SeReportsLogin access for username={}, pinnumber={}", username, pinnumber);
        
        Optional<com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts> userAccount =
                userAccountsRepo.findByUserNameAndPinnumber(username, pinnumber);

        if (!userAccount.isPresent()) {
            log.warn("[STEP 2] ✗ FAILED - User account not found");
            throw new IllegalAccessException("You do not have access for SeReports.");
        }

        com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts account = userAccount.get();
        String seReportsLogin = account.getSeReportsLogin();

        log.debug("[STEP 2] SeReportsLogin column value: '{}'", seReportsLogin);

        if (seReportsLogin == null || seReportsLogin.trim().isEmpty() || !seReportsLogin.trim().equals("1")) {
            log.warn("[STEP 2] ✗ FAILED - No SeReportsLogin access. Value='{}'", seReportsLogin);
            throw new IllegalAccessException("You do not have access for SeReports.");
        }
        
        log.info("[STEP 2] ✓ PASSED - SeReportsLogin access granted");
    }

    public void validateSubscriptionExpiry(String pinnumber)
            throws PaymentRequiredException {
        log.info("[STEP 3] Validating subscription expiry for pinnumber={}", pinnumber);
        
        Optional<CompanyDetails> companyDetails = companyDetailsRepo.findByPinnumber(pinnumber);

        if (!companyDetails.isPresent()) {
            log.warn("[STEP 3] ✗ FAILED - Company details not found");
            throw new PaymentRequiredException("Company details not found.");
        }

        CompanyDetails company = companyDetails.get();
        java.time.LocalDate expiryDate = company.getExpiryDate();
        LocalDate today = LocalDate.now();

        log.info("[STEP 3] ExpiryDate from DB: {}", expiryDate);
        log.info("[STEP 3] Today's Date: {}", today);

        if (expiryDate == null) {
            log.warn("[STEP 3] ✗ FAILED - ExpiryDate is NULL");
            throw new PaymentRequiredException("Expiry date not configured.");
        }

        // CRITICAL: Compare dates properly
        if (expiryDate.isBefore(today)) {
            log.error("[STEP 3] ✗ FAILED - SUBSCRIPTION EXPIRED");
            log.error("[STEP 3] ExpiryDate ({}) is BEFORE today ({})", expiryDate, today);
            throw new PaymentRequiredException("Your SeReports Subscription has expired.");
        }
        
        log.info("[STEP 3] ✓ PASSED - Subscription valid until: {}", expiryDate);
    }

    public com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts getUserAccountByUsername(String username) {
        Optional<com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts> userAccount =
                userAccountsRepo.findByUserName(username);

        if (!userAccount.isPresent()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return userAccount.get();
    }

    private ApplicationUser buildApplicationUserForSystemUser(CompanyDetails systemUser) {
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        if (systemUser.getUserType().trim().equals("Admin") || systemUser.getUserType().trim().equals("ADMIN")) {
            grantedAuthorities.addAll(ADMIN.getGrantedAuthorities());
        }
        
        boolean isActive = systemUser.getStatus().trim().equalsIgnoreCase("Active");
        
        return new ApplicationUser(
                systemUser.getPassword().trim(),
                systemUser.getUsername(),
                grantedAuthorities,
                true,
                true,
                true,
                isActive,
                systemUser.getCompanyId()
        );
    }
}