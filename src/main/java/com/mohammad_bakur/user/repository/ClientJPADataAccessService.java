package com.mohammad_bakur.user.repository;

import com.mohammad_bakur.user.models.Client;
import com.mohammad_bakur.user.ClientDAO;
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
    public List<Client> selectAllUser() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> selectUserById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public void insertUser(Client client) {
        clientRepository.save(client);
    }
    @Override
    public boolean existsPersonWithEmail(String email) {
        return clientRepository.existsUserByEmail(email);
    }
    @Override
    public boolean existsPersonWithId(Integer id) {
        return clientRepository.existsUserById(id);
    }
    @Override
    public void deleteUserById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void updateUser(Client entity) {
        clientRepository.save(entity);
    }
}
