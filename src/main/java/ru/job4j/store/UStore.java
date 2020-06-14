package ru.job4j.store;

import ru.job4j.model.User;

public interface UStore {

    User findUser(String name);

    void saveUser(User user);
}
