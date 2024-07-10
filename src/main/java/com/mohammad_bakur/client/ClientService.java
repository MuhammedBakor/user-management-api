package com.mohammad_bakur.client;


import com.mohammad_bakur.client.requests.ClientRegistrationRequest;
import com.mohammad_bakur.client.requests.ClientUpdateRequest;
import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import com.mohammad_bakur.client.models.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDAO clientDao;

    public ClientService(@Qualifier("jdbc") ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAllClients(){
        return clientDao.selectAllClients();
    }

    public Client getClient(Integer id){
        return clientDao.selectClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "User with id [%s] not found".formatted(id)));
    }

    public void addClient(ClientRegistrationRequest request){
        // check if email exists
        String email = request.email();
        if (clientDao.existsClientWithEmail(email)) {
            throw new ResourceDuplicatedException(
                    "email already taken"
            );
        }

        // add
        Client client = new Client(
                request.name(),
                request.email(),
                request.age()
        );
        clientDao.insertClient(client);
    }

    public void deleteClientById(Integer id) {
        if (!clientDao.existsClientWithId(id)) {
            throw new ResourceNotFoundException(
                    "Client with id [%s] not found".formatted(id)
            );
        }

        clientDao.deleteClientById(id);
    }

    public void updateClient(Integer id,
                             ClientUpdateRequest request) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Client client = getClient(id);

        boolean changes = false;

        if (request.name() != null && !request.name().equals(client.getName())) {
            client.setName(request.name());
            changes = true;
        }

        if (request.age() != null && !request.age().equals(client.getAge())) {
            client.setAge(request.age());
            changes = true;
        }

        if (request.email() != null && !request.email().equals(client.getEmail())) {
            if (clientDao.existsClientWithEmail(request.email())) {
                throw new ResourceDuplicatedException(
                        "email already taken"
                );
            }
            client.setEmail(request.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        clientDao.updateClient(client);
    }
}

