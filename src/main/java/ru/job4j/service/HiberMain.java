package ru.job4j.service;

import ru.job4j.model.Item;
import ru.job4j.model.User;

public class HiberMain {
    public static void main(String[] args) {
        UserService service = UserService.getInstance();
        ItemService itemService = ItemService.getInstance();

        User user1 = new User("Max", "test1");
        User user2 = new User("Misha", "test2");

        User user3 = new User();
        user3.setId(1);
        Item item = new Item("test", true, user3);
        Item item1 = new Item("test1", true, user3);
        Item item2 = new Item("test2", true, user3);

        service.addUser(user1);
        service.addUser(user2);

        itemService.addItem(item);
        itemService.addItem(item1);
        itemService.addItem(item2);

        itemService.doneItem(3);

        for (Item val : itemService.findAllItems(1)) {
            System.out.println(val);
        }

        for (Item val : itemService.findActiveItems(1)) {
            System.out.println(val);
        }
    }
}
