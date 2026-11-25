package com.exam.microservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exam.microservice.dto.request.AccountRequestDto;
import com.exam.microservice.entities.AccountEntity;
import com.exam.microservice.enums.AccountTypeEnum;
import com.exam.microservice.repositories.AccountRepository;
import com.exam.microservice.services.AccountService;

@Service 
public class AccountServiceImpl implements AccountService {

    @Autowired 
    private AccountRepository accountRepo;

    @Override
    public AccountEntity create(AccountRequestDto dto) {
        AccountEntity entity = new AccountEntity();

        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerMobile(dto.getCustomerMobile());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setAddress1(dto.getAddress1());
        entity.setAddress2(dto.getAddress2() != null ? dto.getAddress2() : "N/A");
        entity.setAvailableBalance(500L);

        if(dto.getAccountType().equalsIgnoreCase("savings")) {
            entity.setAccountType(AccountTypeEnum.S);
        }
        else if(dto.getAccountType().equalsIgnoreCase("checking")) {
            entity.setAccountType(AccountTypeEnum.C);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type should either be SAVINGS or CHECKING");
        }
        
        accountRepo.save(entity);

        entity.setAccountNumber(entity.getId());
        accountRepo.save(entity);

        return entity;
    }

    @Override
    public AccountEntity view(Long customerNumber) {
        AccountEntity entity = accountRepo.findById(customerNumber)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Customer not found"));

        return entity;
    }

}
