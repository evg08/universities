package org.levelup.universities.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.util.ArrayList;

//Extend Selection
@RequiredArgsConstructor
public class HibernateFacultyRepository implements FacultyRepository {
    private final SessionFactory factory;

    //get/load
    //-достают значение сущности по id

    @Override
    public FacultyEntity createFaculty(Integer universityId, Integer facultyId, String name) {
        try (Session s = factory.openSession()) {
            Transaction t = s.beginTransaction();

            FacultyEntity faculty = new FacultyEntity();
            faculty.setFacultyId(facultyId);
            faculty.setName(name);
            faculty.setUniversity(s.load(UniversityEntity.class, universityId));
            faculty.setSubjects(new ArrayList<>());

            s.persist(faculty);

            t.commit();
            return faculty;
        }
    }


    @Override
    public FacultyEntity getById(Integer facultyId) {
        try(Session s=factory.openSession()){
            return s.get(FacultyEntity.class,facultyId);
   }
    }

    @Override
    public FacultyEntity loadById(Integer facultyId) {
        try(Session s=factory.openSession()){ return s.load(FacultyEntity.class,facultyId);
        }
        //Session is enherited interface Autocloseeable and session closed by own
//        Session s=null;
//        try{
//            s=factory.openSession();
//            return s.load(FacultyEntity.class,facultyId);
//        }

    }
}
