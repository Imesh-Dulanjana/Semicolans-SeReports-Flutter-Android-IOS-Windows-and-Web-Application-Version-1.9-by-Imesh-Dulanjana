package com.ms.semicolans.sereportapi.sereportapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Catches any /api/** request that isn't handled by a specific controller.
 * Returns a 200 with empty data so the Flutter app doesn't get a 403.
 * Replace with real controllers when you're ready.
 */
@RestController
public class FallbackController {

    @RequestMapping("/api/**")
    public ResponseEntity<Map<String, Object>> fallback() {
        return ResponseEntity.ok(Map.of(
            "message", "ok",
            "data", Collections.emptyList()
        ));
    }
}