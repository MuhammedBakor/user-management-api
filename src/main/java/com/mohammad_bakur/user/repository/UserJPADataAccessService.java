package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class UserJPADataAccessService implements UserDAO {

    private final UserRepository userRepository;

    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<Usert> selectAllClients() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Usert> selectClientById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void insertClient(Usert usert) {
        userRepository.save(usert);
    }
    @Override
    public boolean existsClientWithEmail(String email) {
        return userRepository.existsClientByEmail(email);
    }
    @Override
    public boolean existsClientWithId(Integer id) {
        return userRepository.existsClientById(id);
    }
    @Override
    public void deleteClientById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateClient(Usert entity) {
        userRepository.save(entity);
    }
}
