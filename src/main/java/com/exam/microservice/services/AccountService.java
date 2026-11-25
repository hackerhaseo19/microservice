package com.exam.microservice.services;

import com.exam.microservice.dto.request.AccountRequestDto;
import com.exam.microservice.entities.AccountEntity;

public interface AccountService {

    public AccountEntity create(AccountRequestDto dto);
    public AccountEntity view(Long customerNumber);
}
