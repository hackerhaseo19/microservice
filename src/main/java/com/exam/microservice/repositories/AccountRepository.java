package com.exam.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.microservice.entities.AccountEntity;

@Repository 
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
