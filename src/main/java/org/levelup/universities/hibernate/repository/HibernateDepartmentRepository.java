package org.levelup.universities.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.universities.hibernate.domain.*;

import javax.persistence.EntityManager;
import java.util.Collection;

public class HibernateDepartmentRepository extends AbstractRepository implements DepartmentRepository {
    public HibernateDepartmentRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public DepartmentEntity createDepartment(String name, FacultyEntity faculty) {
    /*   try (Session s = factory.openSession()) { //открываем сессию(соединение к базе)
            Transaction transaction = s.beginTransaction();//начали транзакцию

            DepartmentEntity department = new DepartmentEntity(name,faculty);
            s.persist(department);//делаем инсерт в таблицу university
            transaction.commit();//завершаем транзакцию,фиксируем все изменения,сделанные в транзакции
            return department;
        }*/

        return runWithTransaction((s, f) -> {
            DepartmentEntity department = new DepartmentEntity(name, faculty);
            s.persist(department);
            return department;
        });
    }

    @Override
    public DepartmentEntity findById(int id) {
        return run(s -> s.createQuery("from DepartmentEntity where id=:id", DepartmentEntity.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public Collection<DepartmentEntity> findAll() {
       return run(s-> s.createQuery("from DepartmentEntity", DepartmentEntity.class).getResultList());
    }

    @Override
    public void deleteById(int id) {
        runWithTransaction(session -> {
            DepartmentEntity department=session.get(DepartmentEntity.class,id);
                    session.remove(department);
                    return null;
                }
        );


    }



}
