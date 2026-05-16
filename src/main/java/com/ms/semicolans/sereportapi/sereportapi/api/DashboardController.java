package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.service.DashboardService;
import com.ms.semicolans.sereportapi.sereportapi.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/dashboards")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(path = {"/summary"})
    public ResponseEntity<StandardResponse> getAllBankDetails
            (@RequestHeader("Authorization") String token, @RequestParam String dateFrom,
             @RequestParam String dateTo,
             @RequestParam String locationCode) throws SQLException {
        return new ResponseEntity<>(
                new StandardResponse(200, "All summary", dashboardService.getDashboardSummary(token,dateFrom, dateTo, locationCode))
                , HttpStatus.OK);
    }
}