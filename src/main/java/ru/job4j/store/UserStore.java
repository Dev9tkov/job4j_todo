package ru.job4j.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.model.User;


import java.util.List;

import java.util.function.Function;

public class UserStore implements UStore{

    private static final Logger LOG = LogManager.getLogger(UserStore.class.getName());
    private static final HibernateSesFactUtil CONNECT = HibernateSesFactUtil.getInstance();

    private static final UserStore INSTANCE = new UserStore();

    private UserStore() {
    }

    public static UserStore getInstance() {
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
    public void saveUser(User user) {
        this.tx(session -> session.save(user));
    }

    @Override
    public User findUser(String name) {
        User rsl = null;
        List<User> users = this.tx(session -> session.createQuery("from j_user where name = :name")
            .setParameter("name", name)
            .list());
        if (!users.isEmpty()) {
            rsl = users.get(0);
        }
        return rsl;
    }
}
