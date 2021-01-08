package org.levelup.universities.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.util.Collection;

//@RequiredArgsConstructor //создает конструктор только с final полями
public class HibernateUniversityRepository extends AbstractRepository implements UniversityRepository {
   // private final SessionFactory factory;
    public HibernateUniversityRepository(SessionFactory factory){
        super(factory);
    }

    @Override
    public UniversityEntity createUniversity(String name, String shortName, int foundationYear) {
        try (Session s = factory.openSession()) { //открываем сессию(соединение к базе)
            Transaction transaction = s.beginTransaction();//начали транзакцию

            UniversityEntity university = new UniversityEntity(name, shortName, foundationYear);
            //persist автоматически сдалает setId() у объекта   university
            s.persist(university);//делаем инсерт в таблицу university
            //Integer id=(Integer)s.save(university);
            //UniversityEntity u=(UniversityEntity) s.merge(university);
            transaction.commit();//завершаем транзакцию,фиксируем все изменения,сделанные в транзакции
            return university;
        }
    }

    @Override
    public UniversityEntity createUniversity(String name, String shortName, int foundationYear, int facultyId, String facultyName) {
        try (Session s = factory.openSession()) { //открываем сессию(соединение к базе)
            Transaction transaction = s.beginTransaction();//начали транзакцию

            UniversityEntity university = new UniversityEntity(name, shortName, foundationYear);
            //persist автоматически сдалает setId() у объекта   university

            FacultyEntity faculty = new FacultyEntity();
            faculty.setFacultyId(facultyId);
            faculty.setName(facultyName);
            faculty.setUniversity(university);

            s.persist(university);//делаем инсерт в таблицу university
            s.persist(faculty);
            transaction.commit();//завершаем транзакцию,фиксируем все изменения,сделанные в транзакции
            return university;
        }
    }

    @Override
    public UniversityEntity findById(int universityId) {
       /*   try (Session s = factory.openSession()) { //открываем сессию(соединение к базе)
            //s.get(UniversityEntity.class,universityId);
          return s.createQuery("from UniversityEntity where id=:id", UniversityEntity.class)
                    .setParameter("id", universityId)
                    //.getResultList() -return list of university entities
                    .getSingleResult();*/


            return run(s-> s.createQuery("from UniversityEntity where id=:id", UniversityEntity.class)
                    .setParameter("id", universityId)
                    .getSingleResult());

    }

    @Override
    public Collection<UniversityEntity> findAll() {
      /*  try (Session s = factory.openSession()) {
            return s.createQuery("from UniversityEntity", UniversityEntity.class)
                    .getResultList();
        }*/

//        SessionExecutor findAllUniversitiesExecutor=new SessionExecutor<Collection<UniversityEntity>>() {
//            @Override
//            public Collection <UniversityEntity> execute(Session s) {
//                return s.createQuery("from UniversityEntity", UniversityEntity.class)
//                        .getResultList();
//            }
//        };
//        run(findAllUniversitiesExecutor);
//
        return run(s-> s.createQuery("from UniversityEntity", UniversityEntity.class).getResultList());

    }

    @Override
    public UniversityEntity findByName(String name) {
        try (Session s = factory.openSession()) {
            UniversityEntity university = s.createQuery("from UniversityEntity where name=:name", UniversityEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
            System.out.println( university.getFaculties());
            return university;
        }
    }

    @Override
    public UniversityEntity findByShortName(String shortName) {
        try (Session s = factory.openSession()) {
            return s.createQuery("from UniversityEntity where short_name=:shortName", UniversityEntity.class)
                    .setParameter("shortName", shortName)
                    .getSingleResult();
        }
    }

    @Override
    public Collection<UniversityEntity> findByFoundationYear(int foundationYear) {
        try (Session s = factory.openSession()) {
            return s.createQuery("from UniversityEntity where foundation_year=:foundationYear", UniversityEntity.class)
                    .setParameter("foundationYear", foundationYear)
                    .getResultList();
        }
        //return run(session->session.createQuery(..).getSingleResultList());
    }

    @Override
    public Collection<UniversityEntity> findAllFoundedUniversitiesBetweenYears(int foundationYearStart, int foundationYearEnd) {
        try (Session s = factory.openSession()) {
            return s.createQuery("from UniversityEntity where foundation_year BETWEEN :foundationYearStart AND  :foundationYearEnd", UniversityEntity.class)
                    .setParameter("foundationYearStart", foundationYearStart)
                    .setParameter("foundationYearEnd", foundationYearEnd)
                    .getResultList();


    }}
}
