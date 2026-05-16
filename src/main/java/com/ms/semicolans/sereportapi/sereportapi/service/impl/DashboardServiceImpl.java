package com.ms.semicolans.sereportapi.sereportapi.service.impl;

import com.ms.semicolans.sereportapi.sereportapi.dto.responsedto.*;
import com.ms.semicolans.sereportapi.sereportapi.repo.*;
import com.ms.semicolans.sereportapi.sereportapi.service.CompanyUserService;
import com.ms.semicolans.sereportapi.sereportapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final SalesRepo salesRepo;
    private final PurchaseRepo purchaseRepo;
    private final OtherIncomeExpenseRepo otherIncomeExpenseRepo;
    private final StockRepo stockRepo;
    private final BalanceSheetRepo balanceSheetRepo;
    private final CompanyUserService companyUserService;

    @Override
    public ResponseDashboardSummaryDTO getDashboardSummary(String token, String dateFrom, String dateTo, String locationCode) throws SQLException {

        try {
            ResponseCompanyUserDataDTO userAllData = companyUserService.getUserAllData(token);
            String companyId = userAllData.getCompanyId();

            ResponseSalesSummaryDTO salesSummary = salesRepo.getSalesSummary(dateFrom, dateTo, locationCode, companyId);
            ResponsePurchaseSummaryDTO purchaseSummary = purchaseRepo.getPurchaseSummary(dateFrom, dateTo, locationCode, companyId);
            ResponseOtherIncomeExpenseDTO otherIncomeExpense = otherIncomeExpenseRepo.getOtherIncomeExpense(dateFrom, dateTo, locationCode, companyId);
            ResponseStockSummaryDTO stockSummary = stockRepo.getStockSummary(companyId, locationCode);
            ResponseBalanceSheetDTO balanceSheet = balanceSheetRepo.getBalanceSheet(companyId, locationCode);

            ResponseDashboardSummaryDTO dashboardSummary = new ResponseDashboardSummaryDTO();
            dashboardSummary.setSalesSummary(salesSummary);
            dashboardSummary.setPurchaseSummary(purchaseSummary);
            dashboardSummary.setOtherIncomeExpense(otherIncomeExpense);
            dashboardSummary.setStockSummary(stockSummary);
            dashboardSummary.setBalanceSheet(balanceSheet);

            return dashboardSummary;
        } catch (Exception e) {

            throw new SQLException("Failed to retrieve dashboard data", e);
        }
    }
}