package org.levelup.universities.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.config.HibernateСonfiguration;
import org.levelup.universities.hibernate.repository.HibernateSubjectRepository;

public class RemoveApp {
    public static void main(String[] args) {
        SessionFactory factory = HibernateСonfiguration.getFactory();
        HibernateSubjectRepository subjectRepository = new HibernateSubjectRepository(factory);
        subjectRepository.removeSubject(40);
        factory.close();
    }
}
