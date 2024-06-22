package com.mohammad_bakur.client.repository;

import com.mohammad_bakur.client.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsClientByEmail(String email);
    boolean existsClientById(Integer id);
}
