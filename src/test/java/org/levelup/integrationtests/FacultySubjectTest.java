package org.levelup.integrationtests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.config.HibernateConfigurationTest;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.SubjectEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FacultySubjectTest {
    private static SessionFactory factory;
    String name, shortName, facultyName, secondFacultyName, subject, secondSubject;
    int foundationYear, facultyId, secondFacultyId, id, secondId, hours;
    private UniversityRepository universityRepository;
    private FacultyRepository facultyRepository;
    private SubjectRepository subjectRepository;
    private FacultySubjectRepository facultySubjectRepository;
    ;

    public FacultySubjectTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        facultyRepository = new HibernateFacultyRepository(factory);
        subjectRepository = new HibernateSubjectRepository(factory);
        facultySubjectRepository = new HibernateFacultySubjectRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        facultyId = 1;
        secondFacultyId = 2;
        facultyName = "FTK";
        secondFacultyName = "FTK2";
        id = 1;
        secondId = 2;
        hours = 40;
        subject = "subject";
        secondSubject = "subject2";

    }

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String subjQuery = "delete from SubjectEntity";
            s.createQuery(subjQuery).executeUpdate();
            String facQuery = "delete from FacultyEntity";
            s.createQuery(facQuery).executeUpdate();
            String query = "delete from UniversityEntity";
            s.createQuery(query).executeUpdate();
            tx.commit();
        }
    }

    @Test
    public void testWeaveSubjectAndFaculty() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        facultySubjectRepository.weaveSubjectAndFaculty(subjectEntity.getId(), faculty.getFacultyId());
        subjectEntity = subjectRepository.findById(subjectEntity.getId());
        Assertions.assertEquals(subjectEntity.getFaculties().size(), 1);
        Assertions.assertEquals(((List<FacultyEntity>) subjectEntity.getFaculties()).get(0).getFacultyId(), faculty.getFacultyId());
    }

    @Test
    public void testWeaveSubjectAndFaculty_WhenFacultyIdList() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        FacultyEntity secondFaculty = facultyRepository.createFaculty(university.getId(), secondFacultyId, facultyName);
        List<Integer> listFaculty = new ArrayList<>();
        listFaculty.add(faculty.getFacultyId());
        listFaculty.add(secondFaculty.getFacultyId());
        facultySubjectRepository.weaveSubjectAndFaculty(subjectEntity.getId(), listFaculty);
        subjectEntity = subjectRepository.findById(subjectEntity.getId());
        Assertions.assertEquals(subjectEntity.getFaculties().size(), 2);
        List<Integer> result = subjectEntity.getFaculties().stream().map(FacultyEntity::getFacultyId).collect(Collectors.toList());
        Assertions.assertEquals(listFaculty, result);
    }

    @Test
    public void testWeaveSubjectAndFaculty_WhenSubjectIdList() {
        SubjectEntity subjectEntity = subjectRepository.createSubject(id, subject, hours);
        SubjectEntity secondSubjectEntity = subjectRepository.createSubject(secondId, secondSubject, hours);
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);

        List<Integer> listSubjects = new ArrayList<>();
        listSubjects.add(subjectEntity.getId());
        listSubjects.add(secondSubjectEntity.getId());

        facultySubjectRepository.weaveSubjectAndFaculty(listSubjects, faculty.getFacultyId());
        subjectEntity = subjectRepository.findById(subjectEntity.getId());
        Assertions.assertEquals(subjectEntity.getFaculties().size(), 1);
        Assertions.assertEquals(((List<FacultyEntity>) subjectEntity.getFaculties()).get(0).getFacultyId(), facultyId);

        secondSubjectEntity = subjectRepository.findById(secondSubjectEntity.getId());
        Assertions.assertEquals(secondSubjectEntity.getFaculties().size(), 1);
        Assertions.assertEquals(((List<FacultyEntity>) secondSubjectEntity.getFaculties()).get(0).getFacultyId(), facultyId);
    }
}
