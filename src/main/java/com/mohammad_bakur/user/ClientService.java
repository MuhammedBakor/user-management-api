package com.mohammad_bakur.user;


import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import com.mohammad_bakur.user.models.Client;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDAO clientDao;

    public ClientService(@Qualifier("jdbc") ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAllUsers(){
        return clientDao.selectAllUser();
    }

    public Client getUser(Integer id){
        return clientDao.selectUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "User with id [%s] not found".formatted(id)));
    }

    public void addUser(UserRegistrationRequest request){
        // check if email exists
        String email = request.email();
        if (clientDao.existsPersonWithEmail(email)) {
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
        clientDao.insertUser(client);
    }

    public void deleteUserById(Integer id) {
        if (!clientDao.existsPersonWithId(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id)
            );
        }

        clientDao.deleteUserById(id);
    }

    public void updateUser(Integer id,
                               UserUpdateRequest request) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Client client = getUser(id);

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
            if (clientDao.existsPersonWithEmail(request.email())) {
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

        clientDao.updateUser(client);
    }
}

