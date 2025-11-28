package com.bankingsystem.bank_user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.bankingsystem.bank_user_service.model.Client;


@RestResource(path = "clients")
public interface ClientRepository extends JpaRepository<Client, Integer>{

}
