package ru.job4j.service;

import ru.job4j.model.User;
import ru.job4j.store.UserStore;

public class UserService {
    private static final UserStore ITEM_STORE= UserStore.getInstance();

    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserService() {
    }

    public void addUser(User user) {
        ITEM_STORE.saveUser(user);
    }

    public User findUser(String name) {
        return ITEM_STORE.findUser(name);
    }
}
