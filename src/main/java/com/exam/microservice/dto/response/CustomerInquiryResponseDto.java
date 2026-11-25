package com.exam.microservice.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({
    "customerNumber",
    "customerName",
    "customerMobile",
    "customerEmail",
    "address1",
    "address2",
    "savings",
    "transactionStatusCode",
    "transactionStatusDescription"
})

@Data 
public class CustomerInquiryResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long customerNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String customerName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String customerMobile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String customerEmail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String address1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String address2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<SavingsDto> savings;

    String transactionStatusCode;
    String transactionStatusDescription;
}
