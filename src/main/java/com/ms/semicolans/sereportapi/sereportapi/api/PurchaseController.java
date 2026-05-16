package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.dto.responsedto.paginated.PaginatedResponsePurchaseDTO;
import com.ms.semicolans.sereportapi.sereportapi.service.PurchaseService;
import com.ms.semicolans.sereportapi.sereportapi.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(path = "/purchase-details", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getPurchaseDetails(
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false, defaultValue = "All") String locaCode,
            @RequestParam(required = false, defaultValue = "") String searchItem,
            @RequestParam(required = false, defaultValue = "") String searchCategory,
            @RequestParam(required = false, defaultValue = "") String searchSupplier,
            @RequestParam(required = false, defaultValue = "All") String purchaseType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) throws SQLException {



        // If dates are not provided, set defaults
        if (dateFrom == null) {
            dateFrom = LocalDate.now();
        }
        if (dateTo == null) {
            dateTo = LocalDate.now(); // Default to today
        }

        PaginatedResponsePurchaseDTO response = purchaseService.getPurchaseDetails(
                token, locaCode, searchItem, searchCategory, searchSupplier, purchaseType,
                dateFrom, dateTo, page, size
        );

        return new ResponseEntity<>(new StandardResponse(
                200,
                "Purchase details retrieved successfully",
                response
        ), HttpStatus.OK);
    }
}