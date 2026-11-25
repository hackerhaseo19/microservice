package com.exam.microservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data 
public class AccountResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long customerNumber;
    
    String transactionStatusCode;
    String transactionStatusDescription;
}
