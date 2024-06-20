package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsUserByEmail(String email);
    boolean existsUserById(Integer id);
}
