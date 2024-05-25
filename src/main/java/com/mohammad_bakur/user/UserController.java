package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Client;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //	@RequestMapping(path = "/api/va/users", method = RequestMethod.GET)
    @GetMapping("")
    public List<Client> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Client getUser(@PathVariable("id") Integer id){

        return userService.getUser(id);
    }

    @PostMapping
    public void registerUser(
            @RequestBody UserRegistrationRequest request) {
        userService.addUser(request);
    }
    @DeleteMapping("{id}")
    public void deleteUser(
            @PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping("{id}")
    public void updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
    }
}
