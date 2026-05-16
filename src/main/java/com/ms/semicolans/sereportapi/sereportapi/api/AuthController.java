package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.UserAccounts;
import com.ms.semicolans.sereportapi.sereportapi.service.impl.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ApplicationUserServiceImpl authService;

    /**
     * Login endpoint.
     * Expects JSON: { "username": "...", "password": "...", "pinnumber": "..." }
     *
     * Response:
     *   200 OK  → Authorization header contains Bearer <token> + JSON body with user data.
     *   401     → Invalid credentials.
     *   403     → No SeReportsLogin access.
     *   402     → Subscription expired.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        String pinnumber = credentials.get("pinnumber");

        try {
            // Step 1 + 2: validate credentials and get the user row
            UserAccounts user = authService.validateCredentials(username, password, pinnumber);

            // Step 3: check SeReportsLogin
            authService.validateSeReportsAccess(user);

            // Step 4: check subscription expiry
            authService.validateSubscriptionExpiry(pinnumber);

            // All ok – generate JWT
            String token = authService.generateJwt(username);

            // Prepare response body
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("data", user);    // this includes all chk* columns

            // Add JWT to both Authorization header and body (for easier client handling)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            responseBody.put("token", token);  // also include in body as fallback

            return ResponseEntity.ok().headers(headers).body(responseBody);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid Username, Password, or Pin Number."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You do not have access for SeReports."));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                    .body(Map.of("message", "Your SeReports Subscription has expired."));
        }
    }

    /**
     * Returns the full user permissions for the currently authenticated user.
     * The JWT is extracted from the Authorization header.
     */
    @GetMapping("/user-permissions")
    public ResponseEntity<?> getUserPermissions(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String username = authService.getUsernameFromToken(token);
            UserAccounts user = authService.getUserAccountByUsername(username);
            return ResponseEntity.ok(Map.of("data", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid or expired token."));
        }
    }
}