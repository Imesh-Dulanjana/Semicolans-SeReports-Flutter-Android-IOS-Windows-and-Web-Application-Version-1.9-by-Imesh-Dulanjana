import java.time.LocalDate;

import com.ms.semicolans.sereportapi.sereportapi.entity.main.CompanyDetails;

public void validateSubscriptionExpiry(String pinnumber) {
    List<CompanyDetails> companies = companyDetailsRepo.findByPinnumber(pinnumber);
    if (companies.isEmpty()) {
        throw new IllegalStateException("Your SeReports Subscription has expired.");
    }
    CompanyDetails company = companies.get(0);   // take first
    LocalDate expiry = company.getExpiryDate();
    if (expiry == null || expiry.isBefore(LocalDate.now())) {
        throw new IllegalStateException("Your SeReports Subscription has expired.");
    }
}