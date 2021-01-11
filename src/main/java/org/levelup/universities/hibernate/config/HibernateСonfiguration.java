package org.levelup.universities.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.levelup.universities.hibernate.domain.*;

import javax.imageio.spi.ServiceRegistry;
import java.util.Properties;

public final class HibernateСonfiguration {
    private HibernateСonfiguration() {
    }

    private static SessionFactory factory = initializeSessionFactory();

    private static SessionFactory initializeSessionFactory() {
        Configuration configuration =new Configuration()
                .configure();
        return configuration.buildSessionFactory();
    }

    //служит для получения объекта Session Factory
    public static SessionFactory getFactory() {
        return factory;
    }
}
