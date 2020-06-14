package ru.job4j.store;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.util.Properties;


/**
 * Задача класса- создать для нашего приложения фабрику сессий для работы с БД
 */
public class HibernateSesFactUtil {

    private static SessionFactory sessionFactory;

    private static final HibernateSesFactUtil INSTANCE = new HibernateSesFactUtil();

    private HibernateSesFactUtil() {
    }

    public static HibernateSesFactUtil getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties app = new Properties();
                app.load(HibernateSesFactUtil.class
                        .getClassLoader()
                        .getResourceAsStream("app.properties"));

                Configuration config = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, app.getProperty("driver"));
                settings.put(Environment.URL, app.getProperty("url"));
                settings.put(Environment.USER, app.getProperty("username"));
                settings.put(Environment.PASS, app.getProperty("password"));
                settings.put(Environment.DIALECT, app.getProperty("dialect"));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, app.getProperty("thread"));
                settings.put(Environment.SHOW_SQL, app.getProperty("show_sql"));
                settings.put(Environment.HBM2DDL_AUTO, app.getProperty("hbm2ddl"));

                config.setProperties(settings);
                config.addAnnotatedClass(Item.class);
                config.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties());
                sessionFactory = config.buildSessionFactory(builder.build());
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
