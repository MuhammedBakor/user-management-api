package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Usert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usert, Integer> {
    boolean existsClientByEmail(String email);
    boolean existsClientById(Integer id);
}
