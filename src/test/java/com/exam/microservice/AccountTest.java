package com.exam.microservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.exam.microservice.dto.request.AccountRequestDto;
import com.exam.microservice.entities.AccountEntity;
import com.exam.microservice.repositories.AccountRepository;
import com.exam.microservice.services.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

    @Mock
    private AccountRepository accountRepo;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void accountCreationSuccess() {
        AccountRequestDto dto = new AccountRequestDto();
        
        dto.setCustomerName("Test");
        dto.setCustomerMobile("09081234567");
        dto.setCustomerEmail("test12345@gmail.com");
        dto.setAddress1("test");
        dto.setAddress2("test");
        dto.setAccountType("savings");

        accountService.create(dto);

        verify(accountRepo, times(2))
        .save(any(AccountEntity.class));
    }

    @Test
    void accountCreationFail() {
        AccountRequestDto dto = new AccountRequestDto();
        
        dto.setCustomerName("Test");
        dto.setCustomerMobile("09081234567");
        //dto.setCustomerEmail("test12345@gmail.com");
        dto.setAddress1("test");
        dto.setAddress2("test");
        dto.setAccountType("savings");

        when(accountRepo.save(any(AccountEntity.class)))
            .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> accountService.create(dto)
        );
        
        assertEquals("Database error", exception.getMessage());
    }
    
    @Test
    void accountViewSuccess() {
        AccountRequestDto dto = new AccountRequestDto();
        
        dto.setCustomerName("Test");
        dto.setCustomerMobile("09081234567");
        dto.setCustomerEmail("test12345@gmail.com");
        dto.setAddress1("test");
        dto.setAddress2("test");
        dto.setAccountType("savings");

        AccountEntity entity = accountService.create(dto);

        when(accountRepo.findById(1L))
        .thenReturn(Optional.of(entity));

        AccountEntity entityView = accountService.view(1L);

        assertNotNull(entityView);
        assertEquals("Test", entity.getCustomerName());
    }
    
    @Test
    void accountViewFail() {
        Long customerNumber = 1L;

        when(accountRepo.findById(customerNumber))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> accountService.view(customerNumber));
    }
}
