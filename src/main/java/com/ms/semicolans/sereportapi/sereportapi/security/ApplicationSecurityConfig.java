package com.ms.semicolans.sereportapi.sereportapi.security;

import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ms.semicolans.sereportapi.sereportapi.exception.CustomAuthenticationFailureHandler;
import com.ms.semicolans.sereportapi.sereportapi.jwt.JwtConfig;
import com.ms.semicolans.sereportapi.sereportapi.jwt.JwtTokenVerifier;
import com.ms.semicolans.sereportapi.sereportapi.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.ms.semicolans.sereportapi.sereportapi.service.impl.ApplicationUserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserServiceImpl applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public ApplicationSecurityConfig(
            PasswordEncoder passwordEncoder,
            ApplicationUserServiceImpl applicationUserService,
            JwtConfig jwtConfig,
            SecretKey secretKey,
            AuthenticationConfiguration authenticationConfiguration,
            CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.authenticationConfiguration = authenticationConfiguration;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        log.info("ApplicationSecurityConfig initialized");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain");

        // Create the JWT filter with failure handler
        JwtUsernameAndPasswordAuthenticationFilter jwtFilter =
                new JwtUsernameAndPasswordAuthenticationFilter(
                        authenticationConfiguration.getAuthenticationManager(),
                        jwtConfig,
                        secretKey,
                        applicationUserService);
        
        // CRITICAL: Set the failure handler on the JWT filter
        jwtFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        log.info("✓ CustomAuthenticationFailureHandler set on JWT filter");

        http
            .csrf(csrf -> csrf.disable())
            .cors(httpSecurityCorsConfigurer -> 
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(jwtFilter)
            .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey),
                    JwtUsernameAndPasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers(
                            "/api/v1/users/register/**",
                            "/api/v1/users/verify/**",
                            "/api/v1/users/resend/**",
                            "/api/v1/users/verify-reset/**",
                            "/api/v1/users/reset-password/**",
                            "/api/v1/users/forgot-password-verify/**",
                            "/api/v1/*/visitor/**",
                            "/api/v1/test/**",
                            "/api/v1/debug/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/webjars/**")
                    .permitAll()
                    .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}