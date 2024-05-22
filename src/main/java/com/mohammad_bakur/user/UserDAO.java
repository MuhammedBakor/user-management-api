package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.User;

import java.util.List;
import java.util.Optional;


public interface UserDAO {
    public List<User> selectAllUser();
    public Optional<User> selectUserById(Integer id);
    void insertUser(User user);
    boolean existsPersonWithEmail(String email);
    boolean existsPersonWithId(Integer id);
    void deleteUserById(Integer id);
    void updateUser(User entity);
}
