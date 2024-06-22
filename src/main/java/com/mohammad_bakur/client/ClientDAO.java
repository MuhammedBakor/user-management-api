package com.mohammad_bakur.client;

import com.mohammad_bakur.client.models.Client;

import java.util.List;
import java.util.Optional;


public interface ClientDAO {
    public List<Client> selectAllClients();
    public Optional<Client> selectClientById(Integer id);
    void insertClient(Client client);
    boolean existsClientWithEmail(String email);
    boolean existsClientWithId(Integer id);
    void deleteClientById(Integer id);
    void updateClient(Client entity);
}
