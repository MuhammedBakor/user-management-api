package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Client;
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
    public List<Client> selectAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Client> selectUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void insertUser(Client client) {
        userRepository.save(client);
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
    public void updateUser(Client entity) {
        userRepository.save(entity);
    }
}
