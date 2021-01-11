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
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.FacultyRepository;
import org.levelup.universities.hibernate.repository.HibernateFacultyRepository;
import org.levelup.universities.hibernate.repository.HibernateUniversityRepository;
import org.levelup.universities.hibernate.repository.UniversityRepository;

public class FacultyTest {
    private static SessionFactory factory;
    private UniversityRepository universityRepository;
    private FacultyRepository facultyRepository;
    String name, shortName, facultyName;
    int foundationYear, facultyId;

    public FacultyTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        facultyRepository = new HibernateFacultyRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        facultyId = 1;
        facultyName = "FTK";

    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String facQuery = "delete from FacultyEntity";
            s.createQuery(facQuery).executeUpdate();
            String query = "delete from UniversityEntity";
            s.createQuery(query).executeUpdate();
            tx.commit();
        }
    }

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @Test
    public void testCreateFaculty_thenReturnFacultyEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        Assertions.assertEquals(faculty.getFacultyId(), facultyId);
        Assertions.assertEquals(faculty.getName(), facultyName);
        Assertions.assertEquals(faculty.getUniversity().getId(), university.getId());
    }

    @Test
    public void testGetById_whenIdIsExist_thenReturnFacultyEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        FacultyEntity searchFaculty = facultyRepository.getById(faculty.getFacultyId());
        Assertions.assertEquals(faculty.getFacultyId(), searchFaculty.getFacultyId());
        Assertions.assertEquals(faculty.getName(), searchFaculty.getName());
        Assertions.assertEquals(faculty.getUniversity().getId(), searchFaculty.getUniversity().getId());
    }

    @Test
    public void testLoadById_whenIdIsExist_thenReturnFacultyEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        FacultyEntity searchFaculty = facultyRepository.loadById(faculty.getFacultyId());
        Assertions.assertEquals(faculty.getFacultyId(), searchFaculty.getFacultyId());
    }
}
