package org.levelup.universities.hibernate.repository;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.domain.DepartmentEntity;
import org.levelup.universities.hibernate.domain.StudentEntity;
import org.levelup.universities.hibernate.domain.StudentGroupEntity;

import java.util.Collection;

public class HibernateStudentGroupRepositoryRepository extends AbstractRepository implements StudentGroupRepository {
    public HibernateStudentGroupRepositoryRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public StudentGroupEntity createStudentGroup(String groupKey, DepartmentEntity department) {
        return runWithTransaction((s, f) -> {
            StudentGroupEntity studentGroup = new StudentGroupEntity(groupKey, department);
            s.persist(studentGroup);
            return studentGroup;
        });
    }

    @Override
    public StudentGroupEntity findByGroupKey(String groupKey) {
        return run(s -> s.createQuery("from StudentGroupEntity where groupKey=:groupKey", StudentGroupEntity.class)
                .setParameter("groupKey", groupKey)
                .getSingleResult());
    }

    @Override
    public Collection<StudentGroupEntity> findAll() {
        return run(s-> s.createQuery("from StudentGroupEntity", StudentGroupEntity.class).getResultList());
    }

    @Override
    public void deleteByGroupKey(String groupKey) {
        runWithTransaction(session -> {
            StudentGroupEntity studentGroup=session.get(StudentGroupEntity.class,groupKey);
                    session.remove(studentGroup);
                    return null;
                }
        );
    }
}
