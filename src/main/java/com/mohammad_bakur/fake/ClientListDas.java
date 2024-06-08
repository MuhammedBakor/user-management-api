package com.mohammad_bakur.fake;

import com.mohammad_bakur.user.models.Client;
import com.mohammad_bakur.user.ClientDAO;
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
    public List<Client> selectAllUser() {
        return clients;
    }

    @Override
    public Optional<Client> selectUserById(Integer id) {
            return clients.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
    }

    @Override
    public void insertUser(Client client) {
        clients.add(client);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return clients.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return clients.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteUserById(Integer id) {
        clients.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(/* users::remove */u-> clients.remove(u));
    }

    @Override
    public void updateUser(Client entity) {
        clients.add(entity);
    }
}
