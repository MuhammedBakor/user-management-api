package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Usert;

import java.util.List;
import java.util.Optional;


public interface UserDAO {
    public List<Usert> selectAllClients();
    public Optional<Usert> selectClientById(Integer id);
    void insertClient(Usert usert);
    boolean existsClientWithEmail(String email);
    boolean existsClientWithId(Integer id);
    void deleteClientById(Integer id);
    void updateClient(Usert entity);
}
