package com.mohammad_bakur.client;

import com.mohammad_bakur.client.requests.ClientRegistrationRequest;
import com.mohammad_bakur.client.models.Client;
import com.mohammad_bakur.client.requests.ClientUpdateRequest;
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
            @RequestBody ClientRegistrationRequest request) {
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
            @RequestBody ClientUpdateRequest request) {
        clientService.updateUser(id, request);
    }
}
