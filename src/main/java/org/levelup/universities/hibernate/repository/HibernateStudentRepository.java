package org.levelup.universities.hibernate.repository;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.domain.StudentEntity;
import org.levelup.universities.hibernate.domain.StudentGroupEntity;
import org.levelup.universities.hibernate.domain.SubjectEntity;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public class HibernateStudentRepository extends AbstractRepository implements StudentRepository {
    public HibernateStudentRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public StudentEntity createStudent(Integer studentCard,String lastName, String firstName, Date birthday, StudentGroupEntity studentGroup) {
        return runWithTransaction((s, f) -> {
            StudentEntity student = new StudentEntity(studentCard,lastName, firstName, birthday, studentGroup);
            s.persist(student);
            return student;
        });
    }

    @Override
    public StudentEntity findByStudentCard(Integer studentCard) {
        return run(s -> s.createQuery("from StudentEntity where studentCard=:studentCard", StudentEntity.class)
                .setParameter("studentCard", studentCard)
                .getSingleResult());
    }

    @Override
    public Collection<StudentEntity> findByLastName(String lastName) {
        return run(s -> s.createQuery("from StudentEntity where lastName=:lastName", StudentEntity.class)
                .setParameter("lastName", lastName)
                .getResultList());
    }

    @Override
    public Collection<StudentEntity> findAll() {
        return run(s-> s.createQuery("from StudentEntity", StudentEntity.class).getResultList());

    }

    @Override
    public void deleteByStudentCard(Integer studentCard) {
        runWithTransaction(session -> {
            StudentEntity student=session.get(StudentEntity.class,studentCard);
                    session.remove(student);
                    return null;
                }
        );

    }
}
