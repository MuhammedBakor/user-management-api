package com.mohammad_bakur.user;


import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDao;

    public UserService(@Qualifier("jpa") UserDAO userDao) {
        this.userDao = userDao;
    }

    public List<Usert> getAllClients(){
        return userDao.selectAllClients();
    }

    public Usert getClient(Integer id){
        return userDao.selectClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "User with id [%s] not found".formatted(id)));
    }

    public void addClient(UserRegistrationRequest request){
        // check if email exists
        String email = request.email();
        if (userDao.existsClientWithEmail(email)) {
            throw new ResourceDuplicatedException(
                    "email already taken"
            );
        }

        // add
        Usert usert = new Usert(
                request.name(),
                request.email(),
                request.age()
        );
        userDao.insertClient(usert);
    }

    public void deleteClientById(Integer id) {
        if (!userDao.existsClientWithId(id)) {
            throw new ResourceNotFoundException(
                    "Client with id [%s] not found".formatted(id)
            );
        }

        userDao.deleteClientById(id);
    }

    public void updateClient(Integer id,
                             UserUpdateRequest request) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Usert usert = getClient(id);

        boolean changes = false;

        if (request.name() != null && !request.name().equals(usert.getName())) {
            usert.setName(request.name());
            changes = true;
        }

        if (request.age() != null && !request.age().equals(usert.getAge())) {
            usert.setAge(request.age());
            changes = true;
        }

        if (request.email() != null && !request.email().equals(usert.getEmail())) {
            if (userDao.existsClientWithEmail(request.email())) {
                throw new ResourceDuplicatedException(
                        "email already taken"
                );
            }
            usert.setEmail(request.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        userDao.updateClient(usert);
    }
}

