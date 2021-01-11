package org.levelup.universities.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.levelup.universities.hibernate.domain.*;

import java.util.Properties;

public class HibernateConfigurationTest {
    private HibernateConfigurationTest() {
    }

    private static SessionFactory factory = initializeSessionFactory();

    private static SessionFactory initializeSessionFactory() {
        //  Configuration configuration =new Configuration()
        //        .configure();
        if (factory==null){
            try {

                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/universities_2");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "admin");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL10Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(UniversityEntity.class);
                configuration.addAnnotatedClass(FacultyEntity.class);
                configuration.addAnnotatedClass(SubjectEntity.class);
                configuration.addAnnotatedClass(SubjectInfoEntity.class);
                configuration.addAnnotatedClass(DepartmentEntity.class);
                configuration.addAnnotatedClass(StudentGroupEntity.class);
                configuration.addAnnotatedClass(StudentEntity.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                factory=configuration.buildSessionFactory(builder.build());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return factory;
        //  return configuration.buildSessionFactory();
    }

    //служит для получения объекта Session Factory
    public static SessionFactory getFactory() {
        return factory;
    }
}
