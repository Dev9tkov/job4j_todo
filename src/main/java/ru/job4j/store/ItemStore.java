package ru.job4j.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemStore implements IStore{

    private static final Logger LOG = LogManager.getLogger(ItemStore.class.getName());
    private static final HibernateSesFactUtil CONNECT = HibernateSesFactUtil.getInstance();

    private static final ItemStore INSTANCE = new ItemStore();

    private ItemStore() {
    }

    public static ItemStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Item item) {
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            session.save(item);
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> getItems() {
        List rsl = new ArrayList<>();
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            /**
             * показать все заявки. И выполненные, и нет
             */
            rsl = session.createQuery("From Item").list();
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }


    @Override
    public List<Item> getActiveItems() {
        List rsl = new ArrayList<>();
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            /**
             * Показать только те заявки, которые ожидают выполнения
             */
            rsl = session.createQuery("From Item as I where I.done = true").list();
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Item getItem(Item item) {
        Item rsl = null;
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            rsl = session.get(Item.class, item.getId());
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public void delete(Integer id) {
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            session.createQuery("delete from Item as I where I.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void doneItem(Integer id) {
        Transaction txn;
        Session session = CONNECT.getSessionFactory().openSession();
        try {
            txn = session.beginTransaction();
            session.createQuery("update Item as I set done = false where I.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            txn.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
