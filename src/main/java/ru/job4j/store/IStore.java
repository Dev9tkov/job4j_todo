package ru.job4j.store;

import ru.job4j.model.Item;

import java.util.Collection;
import java.util.List;

public interface IStore {
    void save(Item item);

    List<Item> getItems();

    Item getItem(Item item);

    void delete(Integer id);

    List<Item> getActiveItems();

    void doneItem(Integer id);

}
