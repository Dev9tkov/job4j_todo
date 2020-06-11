package ru.job4j.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.model.Item;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemStore implements IStore{

    private static final Logger LOG = LogManager.getLogger(ItemStore.class.getName());
    private static final HibernateSesFactUtil CONNECT = HibernateSesFactUtil.getInstance();

    private static final ItemStore INSTANCE = new ItemStore();

    private ItemStore() {
    }

    public static ItemStore getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = CONNECT.getSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
           T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void save(Item item) {
        this.tx(session -> session.save(item));
    }

    /**
     * показать все заявки. И выполненные, и нет
     */
    @Override
    public List<Item> getItems(Integer userId) {
        return this.tx(session -> {
            Query query = session.createQuery("FROM ru.job4j.model.Item AS i WHERE i.user.id= :id");
            query.setParameter("id", userId);
            return (List<Item>) query.getResultList();
        });
    }


    /**
     * Показать только те заявки, которые ожидают выполнения
     */
    @Override
    public List<Item> getActiveItems(Integer userId) {
        return getItems(userId)
                .stream()
                .filter(Item::isDone)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        this.tx(session -> session.createQuery("delete from Item as I where I.id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    @Override
    public void doneItem(Integer id) {
        this.tx(session -> session.createQuery("update Item as I set done = false where I.id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }
}
