package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Client;

import java.util.List;
import java.util.Optional;


public interface ClientDAO {
    public List<Client> selectAllUser();
    public Optional<Client> selectUserById(Integer id);
    void insertUser(Client client);
    boolean existsPersonWithEmail(String email);
    boolean existsPersonWithId(Integer id);
    void deleteUserById(Integer id);
    void updateUser(Client entity);
}
