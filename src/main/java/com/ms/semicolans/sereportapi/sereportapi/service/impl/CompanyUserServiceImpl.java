package com.ms.semicolans.sereportapi.sereportapi.service.impl;

import java.sql.SQLException;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ms.semicolans.sereportapi.sereportapi.dto.responsedto.ResponseCompanyUserDataDTO;
import com.ms.semicolans.sereportapi.sereportapi.entity.main.CompanyDetails;
import com.ms.semicolans.sereportapi.sereportapi.jwt.JwtConfig;
import com.ms.semicolans.sereportapi.sereportapi.repo.CompanyDetailsRepo;
import com.ms.semicolans.sereportapi.sereportapi.service.CompanyUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyUserServiceImpl implements CompanyUserService {
    private final CompanyDetailsRepo companyDetailsRepo;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    @Override
    public ResponseCompanyUserDataDTO getUserAllData(String token) throws SQLException {
        String realToken = token.replace(jwtConfig.getTokenPrefix(), "");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(realToken);
        String username = claimsJws.getBody().getSubject();
        Optional<CompanyDetails> selectedUser = companyDetailsRepo.findByUsername(username);
        if (selectedUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new ResponseCompanyUserDataDTO(
                selectedUser.get().getCompanyId(),
                selectedUser.get().getUserType()
        );
    }
}
