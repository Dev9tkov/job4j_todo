package ru.job4j.store;

import ru.job4j.model.Item;

import java.util.List;

public interface IStore {
    void save(Item item);

    List<Item> getItems();

    void delete(Integer id);

    List<Item> getActiveItems();

    void doneItem(Integer id);

}
