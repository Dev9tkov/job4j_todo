package ru.job4j.store;

import ru.job4j.model.User;

import java.util.Optional;

public interface UStore {
    User findUser(String name);
//    Optional<User> findUser(String name);

    void saveUser(User user);
}
