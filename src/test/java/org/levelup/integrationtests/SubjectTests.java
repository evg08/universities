package org.levelup.integrationtests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.config.HibernateConfigurationTest;
import org.levelup.universities.hibernate.domain.SubjectEntity;
import org.levelup.universities.hibernate.repository.HibernateSubjectRepository;
import org.levelup.universities.hibernate.repository.SubjectRepository;

import javax.persistence.NoResultException;

public class SubjectTests {
    private static SessionFactory factory;
    private SubjectRepository subjectRepository;
    Integer id, hours;
    String subject;

    public SubjectTests() {
        factory = HibernateConfigurationTest.getFactory();
        subjectRepository = new HibernateSubjectRepository(factory);
        id = 1;
        hours = 40;
        subject = "subject";
    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String query = "delete from SubjectEntity";
            s.createQuery(query).executeUpdate();
            tx.commit();
        }
    }

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @Test
    public void testCreateSubject_thenReturnSubjectEntity() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        Assertions.assertEquals(subjectEntity.getId(), id);
        Assertions.assertEquals(subjectEntity.getSubject(), subject);
        Assertions.assertEquals(subjectEntity.getHours(), hours);
    }

    @Test
    public void testFindById_thenReturnSubjectEntity() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        SubjectEntity searchedSubjectEntity = subjectRepository.findById(subjectEntity.getId());
        Assertions.assertEquals(subjectEntity.getId(), searchedSubjectEntity.getId());
        Assertions.assertEquals(subjectEntity.getSubject(), searchedSubjectEntity.getSubject());
        Assertions.assertEquals(subjectEntity.getHours(), searchedSubjectEntity.getHours());
    }

    @Test
    public void testRemoveSubject_thenDeleteSubjectEntity() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        subjectRepository.removeSubject(subjectEntity.getId());
        Assertions.assertThrows(NoResultException.class, () -> {
            subjectRepository.findById(subjectEntity.getId());
        });
    }
}
