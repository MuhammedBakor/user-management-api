package com.mohammad_bakur.user;

import com.mohammad_bakur.user.models.Client;
import com.mohammad_bakur.user.requests.UserRegistrationRequest;
import com.mohammad_bakur.user.requests.UserUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //	@RequestMapping(path = "/api/va/users", method = RequestMethod.GET)
    @GetMapping("/")
    public List<Client> getUsers(){
        return clientService.getAllUsers();
    }

    @GetMapping("{id}")
    public Client getUser(@PathVariable("id") Integer id){

        return clientService.getUser(id);
    }

    @PostMapping
    public void registerUser(
            @RequestBody UserRegistrationRequest request) {
        clientService.addUser(request);
    }
    @DeleteMapping("{id}")
    public void deleteUser(
            @PathVariable("id") Integer id) {
        clientService.deleteUserById(id);
    }

    @PutMapping("{id}")
    public void updateUser(
            @PathVariable("id") Integer id,
            @RequestBody UserUpdateRequest request) {
        clientService.updateUser(id, request);
    }
}
