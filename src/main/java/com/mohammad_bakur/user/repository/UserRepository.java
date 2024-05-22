package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);
    boolean existsUserById(Integer id);
}
