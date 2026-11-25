package com.exam.microservice.dto.response;

import lombok.Data;

@Data 
public class SavingsDto {

    Long accountNumber;
    String accountType;
    Long availableBalance;
}
