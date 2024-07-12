package com.mohammad_bakur.fake;

import com.mohammad_bakur.user.models.Usert;
import com.mohammad_bakur.user.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class UserListDas implements UserDAO {

    //db
    private static List<Usert> userts;

    static{
        userts =new ArrayList<>();

        Usert alex = new Usert(
                1, "Alex", "alex@gmail.com",21
        );

        Usert jamila = new Usert(
                2, "Jamila", "jamila@gmail.com",19
        );

        userts.add(alex);
        userts.add(jamila);
    }

    @Override
    public List<Usert> selectAllClients() {
        return userts;
    }

    @Override
    public Optional<Usert> selectClientById(Integer id) {
            return userts.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
    }

    @Override
    public void insertClient(Usert usert) {
        userts.add(usert);
    }

    @Override
    public boolean existsClientWithEmail(String email) {
        return userts.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsClientWithId(Integer id) {
        return userts.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteClientById(Integer id) {
        userts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(/* users::remove */u-> userts.remove(u));
    }

    @Override
    public void updateClient(Usert entity) {
        userts.add(entity);
    }
}
