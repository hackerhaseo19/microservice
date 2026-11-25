package com.exam.microservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.exam.microservice.dto.request.AccountRequestDto;
import com.exam.microservice.dto.response.AccountResponseDto;
import com.exam.microservice.dto.response.CustomerInquiryResponseDto;
import com.exam.microservice.dto.response.SavingsDto;
import com.exam.microservice.entities.AccountEntity;
import com.exam.microservice.enums.AccountTypeEnum;
import com.exam.microservice.services.AccountService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AccountResponseDto> handleValidationError(
        ConstraintViolationException e) {

    AccountResponseDto response = new AccountResponseDto();

    response.setCustomerNumber(null);
    response.setTransactionStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));

    response.setTransactionStatusDescription(
        e.getConstraintViolations().iterator().next().getMessage()
    );

    return ResponseEntity.badRequest().body(response);
}

    @PostMapping("/account")
    public ResponseEntity<AccountResponseDto> saveAccount(@Valid @RequestBody AccountRequestDto request) {
        AccountResponseDto response = new AccountResponseDto();

        try {
            AccountEntity entity = accountService.create(request);

            response.setCustomerNumber(entity.getId());
            response.setTransactionStatusCode(String.valueOf(HttpStatus.CREATED.value()));
            response.setTransactionStatusDescription("Customer account created");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(ResponseStatusException e) {
            response.setTransactionStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            response.setTransactionStatusDescription(e.getReason());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/account/{customerNumber}")
    public ResponseEntity<CustomerInquiryResponseDto> viewCustomer(@PathVariable("customerNumber") Long customerNumber) {
        CustomerInquiryResponseDto response = new CustomerInquiryResponseDto();
        
        try {
            AccountEntity entity = accountService.view(customerNumber);
            List<SavingsDto> savings = new ArrayList<>();
            SavingsDto savingsDto = new SavingsDto();
            
            savingsDto.setAccountNumber(entity.getAccountNumber());
            savingsDto.setAccountType(entity.getAccountType() == AccountTypeEnum.S ? "Savings" : "Checking");
            savingsDto.setAvailableBalance(entity.getAvailableBalance());
            savings.add(savingsDto);

            response.setCustomerNumber(entity.getId());
            response.setCustomerName(entity.getCustomerName());
            response.setCustomerMobile(entity.getCustomerMobile());
            response.setCustomerEmail(entity.getCustomerEmail());
            response.setAddress1(entity.getAddress1());
            response.setAddress2(entity.getAddress2());

            response.setSavings(savings);
            response.setTransactionStatusCode(String.valueOf(HttpStatus.FOUND.value()));
            response.setTransactionStatusDescription("Customer Account found");

            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }
        catch(ResponseStatusException e) {
            response.setTransactionStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
            response.setTransactionStatusDescription(e.getReason());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
