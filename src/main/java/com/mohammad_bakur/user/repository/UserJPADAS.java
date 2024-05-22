package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.User;
import com.mohammad_bakur.user.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UserJPADAS implements UserDAO {

    private final UserRepository userRepository;

    public UserJPADAS(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> selectAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }
    @Override
    public boolean existsPersonWithEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }
    @Override
    public boolean existsPersonWithId(Integer id) {
        return userRepository.existsUserById(id);
    }
    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User entity) {
        userRepository.save(entity);
    }
}
