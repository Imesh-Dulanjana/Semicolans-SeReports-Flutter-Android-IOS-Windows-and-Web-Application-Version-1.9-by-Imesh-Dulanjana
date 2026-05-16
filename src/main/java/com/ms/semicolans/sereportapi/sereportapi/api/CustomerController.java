package com.ms.semicolans.sereportapi.sereportapi.api;

import com.ms.semicolans.sereportapi.sereportapi.dto.responsedto.paginated.PaginatedResponseCustomerRecordeDTO;
import com.ms.semicolans.sereportapi.sereportapi.service.impl.CustomerServiceImpl;
import com.ms.semicolans.sereportapi.sereportapi.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(path = {"/get-customers-details"})
    public ResponseEntity<StandardResponse> getCustomerDetails(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "All") String invGap,
            @RequestParam(required = false, defaultValue = "false") boolean filterCreditAmount,
            @RequestParam(required = false, defaultValue = "All") String settlement,
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("Authorization") String token
    ) throws SQLException {

        PaginatedResponseCustomerRecordeDTO response = customerService.getCustomerDetails(
                token,
                searchText,
                filterCreditAmount,
                invGap,
                settlement,
                page,
                size
        );

        return new ResponseEntity<>(
                new StandardResponse(
                        HttpStatus.OK.value(),
                        "Customer details",
                        response
                ),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(path = {"/get-debtor-details"})
    public ResponseEntity<StandardResponse> getDebtorDetails(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "All") String invGap,
            @RequestParam(required = false, defaultValue = "All") String settlement,
            @RequestParam(required = false) String creditAmount,
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("Authorization") String token
    ) throws SQLException {

        PaginatedResponseCustomerRecordeDTO response = customerService.getDebtorsDetails(
                token,
                searchText,
                creditAmount,
                invGap,
                settlement,
                page,
                size
        );

        return new ResponseEntity<>(
                new StandardResponse(
                        HttpStatus.OK.value(),
                        "Debtor details",
                        response
                ),
                HttpStatus.OK
        );
    }
}