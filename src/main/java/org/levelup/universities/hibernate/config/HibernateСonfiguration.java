package org.levelup.universities.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
