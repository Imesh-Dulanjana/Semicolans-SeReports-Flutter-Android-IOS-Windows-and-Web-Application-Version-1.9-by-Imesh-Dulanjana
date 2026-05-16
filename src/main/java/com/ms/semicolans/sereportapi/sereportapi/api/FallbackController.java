package com.ms.semicolans.sereportapi.sereportapi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

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