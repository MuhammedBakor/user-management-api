package com.mohammad_bakur.fake;

import com.mohammad_bakur.client.models.Client;
import com.mohammad_bakur.client.ClientDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class ClientListDas implements ClientDAO {

    //db
    private static List<Client> clients;

    static{
        clients =new ArrayList<>();

        Client alex = new Client(
                1, "Alex", "alex@gmail.com",21
        );

        Client jamila = new Client(
                2, "Jamila", "jamila@gmail.com",19
        );

        clients.add(alex);
        clients.add(jamila);
    }

    @Override
    public List<Client> selectAllClients() {
        return clients;
    }

    @Override
    public Optional<Client> selectClientById(Integer id) {
            return clients.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
    }

    @Override
    public void insertClient(Client client) {
        clients.add(client);
    }

    @Override
    public boolean existsClientWithEmail(String email) {
        return clients.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsClientWithId(Integer id) {
        return clients.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteClientById(Integer id) {
        clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(/* users::remove */u-> clients.remove(u));
    }

    @Override
    public void updateClient(Client entity) {
        clients.add(entity);
    }
}
