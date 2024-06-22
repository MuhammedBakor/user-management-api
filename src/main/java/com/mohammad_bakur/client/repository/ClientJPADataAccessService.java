package com.mohammad_bakur.client.repository;

import com.mohammad_bakur.client.models.Client;
import com.mohammad_bakur.client.ClientDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class ClientJPADataAccessService implements ClientDAO {

    private final ClientRepository clientRepository;

    public ClientJPADataAccessService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public List<Client> selectAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> selectClientById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public void insertClient(Client client) {
        clientRepository.save(client);
    }
    @Override
    public boolean existsClientWithEmail(String email) {
        return clientRepository.existsClientByEmail(email);
    }
    @Override
    public boolean existsClientWithId(Integer id) {
        return clientRepository.existsClientById(id);
    }
    @Override
    public void deleteClientById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void updateClient(Client entity) {
        clientRepository.save(entity);
    }
}
