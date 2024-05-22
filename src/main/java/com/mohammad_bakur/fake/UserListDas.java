package com.mohammad_bakur.fake;

import com.mohammad_bakur.user.models.User;
import com.mohammad_bakur.user.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class UserListDas implements UserDAO {

    //db
    private static List<User> users;

    static{
        users =new ArrayList<>();

        User alex = new User(
                1, "Alex", "alex@gmail.com",21
        );

        User jamila = new User(
                2, "Jamila", "jamila@gmail.com",19
        );

        users.add(alex);
        users.add(jamila);
    }

    @Override
    public List<User> selectAllUser() {
        return users;
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
            return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
    }

    @Override
    public void insertUser(User user) {
        users.add(user);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return users.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return users.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteUserById(Integer id) {
        users.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(/* users::remove */u-> users.remove(u));
    }

    @Override
    public void updateUser(User entity) {
        users.add(entity);
    }
}
