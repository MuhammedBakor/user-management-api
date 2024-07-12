package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //	@RequestMapping(path = "/api/va/users", method = RequestMethod.GET)
    @GetMapping("")
    public List<Usert> getUsers(){
        return userService.getAllClients();
    }

    @GetMapping("/{id}")
    public Usert getUser(@PathVariable("id") Integer id){

        return userService.getClient(id);
    }

    @PostMapping
    public void registerUser(
            @RequestBody UserRegistrationRequest request) {
        userService.addClient(request);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable("id") Integer id) {
        userService.deleteClientById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserUpdateRequest request) {
        userService.updateClient(id, request);
    }
}
