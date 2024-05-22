package com.mohammad_bakur.user;


import com.mohammad_bakur.exceptions.RequestValidationException;
import com.mohammad_bakur.exceptions.ResourceDuplicatedException;
import com.mohammad_bakur.exceptions.ResourceNotFoundException;
import com.mohammad_bakur.user.models.User;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDao;

    public UserService(@Qualifier("jpa") UserDAO userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(){
        return userDao.selectAllUser();
    }

    public User getUser(Integer id){
        return userDao.selectUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "User with id [%s] not found".formatted(id)));
    }

    public void addUser(UserRegistrationRequest request){
        // check if email exists
        String email = request.email();
        if (userDao.existsPersonWithEmail(email)) {
            throw new ResourceDuplicatedException(
                    "email already taken"
            );
        }

        // add
        User user = new User(
                request.name(),
                request.email(),
                request.age()
        );
        userDao.insertUser(user);
    }

    public void deleteUserById(Integer id) {
        if (!userDao.existsPersonWithId(id)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(id)
            );
        }

        userDao.deleteUserById(id);
    }

    public void updateUser(Integer id,
                               UserUpdateRequest request) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        User user = getUser(id);

        boolean changes = false;

        if (request.name() != null && !request.name().equals(user.getName())) {
            user.setName(request.name());
            changes = true;
        }

        if (request.age() != null && !request.age().equals(user.getAge())) {
            user.setAge(user.getAge());
            changes = true;
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userDao.existsPersonWithEmail(request.email())) {
                throw new ResourceDuplicatedException(
                        "email already taken"
                );
            }
            user.setEmail(request.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        userDao.updateUser(user);
    }
}

