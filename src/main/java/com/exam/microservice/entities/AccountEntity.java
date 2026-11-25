package com.exam.microservice.entities;

import com.exam.microservice.enums.AccountTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Customer name is required field")
    @Column(name = "customer_name", length = 50, nullable = false)
    @Size(max = 50, message = "Customer name must not exceed 50 characters")
    private String customerName;

    @NotNull(message = "Customer mobile is required field")
    @Column(name = "customer_mobile", length = 20, nullable = false)
    @Size(max = 20, message = "Customer mobile must not exceed 20 characters")
    private String customerMobile;

    @NotBlank(message = "Email is required field")
    @Email(message = "Email has an invalid format")
    @Column(name = "customer_email", length = 50, nullable = false)
    @Size(max = 50, message = "Customer email must not exceed 50 characters")
    private String customerEmail;

    @NotNull(message = "Address 1 is required field")
    @Column(name = "address_1", length = 100, nullable = false)
    @Size(max = 100, message = "Address must not exceed 100 characters")
    private String address1;

    @Column(name = "address_2", length = 100)
    @Size(max = 100, message = "Address must not exceed 100 characters")
    private String address2;

    @NotNull(message = "Account type is required field")
    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "available_balance")
    private Long availableBalance;
}
