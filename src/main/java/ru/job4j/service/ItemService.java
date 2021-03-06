package ru.job4j.service;

import ru.job4j.model.Item;
import ru.job4j.store.ItemStore;

import java.util.List;

public class ItemService {
    private static final ItemStore ITEM_STORE= ItemStore.getInstance();

    private static final ItemService INSTANCE = new ItemService();

    public static ItemService getInstance() {
        return INSTANCE;
    }

    private ItemService() {
    }

    public void addItem(Item item) {
        ITEM_STORE.save(item);
    }

    public List<Item> findAllItems(Integer id) {
        return ITEM_STORE.getItems(id);
    }

    public List<Item> findActiveItems(Integer id) {
        return ITEM_STORE.getActiveItems(id);
    }

    public void deleteItem(Integer id) {
        ITEM_STORE.delete(id);
    }

    public void doneItem(Integer id) {
        ITEM_STORE.doneItem(id);
    }

    public boolean haveAnyItems(Integer id) {
        return ITEM_STORE.haveAnyItems(id);
    }
}
