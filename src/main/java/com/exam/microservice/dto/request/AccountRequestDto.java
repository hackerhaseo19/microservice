package com.exam.microservice.dto.request;

import lombok.Data;

@Data 
public class AccountRequestDto {

    String customerName;
    String customerMobile;
    String customerEmail;
    String address1;
    String address2;
    String accountType;
}
