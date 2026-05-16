package com.ms.semicolans.sereportapi.sereportapi.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.semicolans.sereportapi.sereportapi.exception.CustomAuthenticationException;
import com.ms.semicolans.sereportapi.sereportapi.service.impl.ApplicationUserServiceImpl;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final ApplicationUserServiceImpl applicationUserService;

    public JwtUsernameAndPasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtConfig jwtConfig,
            SecretKey secretKey,
            ApplicationUserServiceImpl applicationUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.applicationUserService = applicationUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("\n\n========== JWT FILTER: LOGIN ATTEMPT STARTED ==========");
        
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(),
                            UsernameAndPasswordAuthenticationRequest.class);

            String username = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();
            String pinnumber = authenticationRequest.getPinnumber();

            log.info("Step 1: Username={}, Pinnumber={}", username, pinnumber);

            // STEP 1: Validate credentials
            try {
                applicationUserService.loadUserByUsernameAndPinnumber(username, pinnumber);
                log.info("✓ Step 1 PASSED: Credentials valid");
            } catch (Exception e) {
                log.error("✗ Step 1 FAILED: {}", e.getMessage());
                throw new CustomAuthenticationException("Invalid Username, Password, or Pin Number.", e);
            }

            // STEP 2: Validate SeReports access
            try {
                log.info("Step 2: Checking SeReportsLogin access...");
                applicationUserService.validateSeReportsAccess(username, pinnumber);
                log.info("✓ Step 2 PASSED: SeReportsLogin access granted");
            } catch (Exception e) {
                log.error("✗ Step 2 FAILED: {}", e.getMessage());
                throw new CustomAuthenticationException("You do not have access for SeReports.", e);
            }

            // STEP 3: Validate subscription expiry - MOST IMPORTANT
            try {
                log.info("Step 3: Checking subscription expiry...");
                applicationUserService.validateSubscriptionExpiry(pinnumber);
                log.info("✓ Step 3 PASSED: Subscription is valid");
            } catch (Exception e) {
                log.error("✗ Step 3 FAILED: {}", e.getMessage());
                throw new CustomAuthenticationException(e.getMessage(), e);
            }

            log.info("========== ALL VALIDATIONS PASSED - AUTHENTICATING ==========");

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authentication);

        } catch (AuthenticationException e) {
            log.error("AuthenticationException in attemptAuthentication: {}", e.getMessage());
            throw e;
        } catch (IOException e) {
            log.error("IOException parsing login request: {}", e.getMessage());
            throw new CustomAuthenticationException("Invalid login request format", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("========== AUTHENTICATION SUCCESSFUL ==========");
        log.info("User: {} authenticated", authResult.getName());
        
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
        
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
        log.info("JWT token issued");
    }
}