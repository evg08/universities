package org.levelup.integrationtests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.config.HibernateConfigurationTest;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.HibernateUniversityRepository;
import org.levelup.universities.hibernate.repository.UniversityRepository;

import java.util.ArrayList;
import java.util.List;

public class UniversityTest {
    private static SessionFactory factory;
    private UniversityRepository universityRepository;
    String name, shortName, secondName, secondShortName, thirdName, thirdShortName;
    int foundationYear, secondFoundationYear, thirdFoundationYear;

    public UniversityTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        secondName = "Ленинградский государственный университет";
        secondShortName = "ЛГУ";
        secondFoundationYear = 1981;

        thirdName = "Московский государственный университет";
        thirdShortName = "МГУ";
        thirdFoundationYear = 1979;
    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
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
    public void testCreateUniversity_withoutFaculty_thenReturnUniversityEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        Assertions.assertEquals(university.getName(), name);
        Assertions.assertEquals(university.getShortName(), shortName);
        Assertions.assertEquals(university.getFoundationYear(), foundationYear);
    }

    @Test
    public void testCreateUniversity_withFaculty_thenReturnUniversityEntity() {
        int facultyId = 1;
        String facultyName = "FTK";
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        Assertions.assertEquals(university.getName(), name);
        Assertions.assertEquals(university.getShortName(), shortName);
        Assertions.assertEquals(university.getFoundationYear(), foundationYear);
    }

    @Test
    public void testFindById_whenIdIsExist_thenReturnUniversityEntity() {

        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity university = universityRepository.findById(universityCreated.getId());
        Assertions.assertEquals(universityCreated.getId(), university.getId());
        Assertions.assertEquals(universityCreated.getName(), university.getName());
        Assertions.assertEquals(universityCreated.getShortName(), university.getShortName());
        Assertions.assertEquals(universityCreated.getFoundationYear(), university.getFoundationYear());
    }

    @Test
    public void testFindByName_whenNameIsExist_thenReturnUniversityEntity() {
        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity university = universityRepository.findByName(universityCreated.getName());
        Assertions.assertEquals(universityCreated.getId(), university.getId());
        Assertions.assertEquals(universityCreated.getName(), university.getName());
        Assertions.assertEquals(universityCreated.getShortName(), university.getShortName());
        Assertions.assertEquals(universityCreated.getFoundationYear(), university.getFoundationYear());
    }

    @Test
    public void testFindByShortName_whenShortNameIsExist_thenReturnUniversityEntity() {
        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity university = universityRepository.findByShortName(universityCreated.getShortName());
        Assertions.assertEquals(universityCreated.getId(), university.getId());
        Assertions.assertEquals(universityCreated.getName(), university.getName());
        Assertions.assertEquals(universityCreated.getShortName(), university.getShortName());
        Assertions.assertEquals(universityCreated.getFoundationYear(), university.getFoundationYear());
    }

    @Test
    public void testFindByFoundationYear_whenFoundationYearIsExist_thenReturnListUniversityEntities() {
        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity universitySecondCreated = universityRepository.createUniversity(secondName, secondShortName, secondFoundationYear);
        UniversityEntity universityThirdCreated = universityRepository.createUniversity(thirdName, thirdShortName, thirdFoundationYear);
        List<UniversityEntity> universities = new ArrayList<>();
        universities.add(universityCreated);
        universities.add(universitySecondCreated);
        universities.add(universityThirdCreated);

        List<UniversityEntity> universityList = (List<UniversityEntity>) universityRepository.findByFoundationYear(foundationYear);
        Assertions.assertEquals(universityList.size(), 2);
        Assertions.assertTrue(universityList.contains(universityCreated));
        Assertions.assertTrue(universityList.contains(universityThirdCreated));
    }

    @Test
    public void testFindAllFoundedUniversitiesBetweenYears_whenFoundationYearsAreExist_thenReturnListUniversityEntities() {
        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity universitySecondCreated = universityRepository.createUniversity(secondName, secondShortName, secondFoundationYear);
        UniversityEntity universityThirdCreated = universityRepository.createUniversity(thirdName, thirdShortName, thirdFoundationYear);
        List<UniversityEntity> universities = new ArrayList<>();
        universities.add(universityCreated);
        universities.add(universitySecondCreated);
        universities.add(universityThirdCreated);
        int yearStart = 1950;
        int yearEnd = 1980;

        List<UniversityEntity> universityList = (List<UniversityEntity>) universityRepository.findAllFoundedUniversitiesBetweenYears(yearStart, yearEnd);
        Assertions.assertEquals(universityList.size(), 2);
        Assertions.assertTrue(universityList.contains(universityCreated));
        Assertions.assertTrue(universityList.contains(universityThirdCreated));
    }

    @Test
    public void testFindAll_whenDataAreExist_thenReturnListUniversityEntities() {
        UniversityEntity universityCreated = universityRepository.createUniversity(name, shortName, foundationYear);
        UniversityEntity universitySecondCreated = universityRepository.createUniversity(secondName, secondShortName, secondFoundationYear);
        UniversityEntity universityThirdCreated = universityRepository.createUniversity(thirdName, thirdShortName, thirdFoundationYear);
        List<UniversityEntity> universities = new ArrayList<>();
        universities.add(universityCreated);
        universities.add(universitySecondCreated);
        universities.add(universityThirdCreated);

        List<UniversityEntity> universityList = (List<UniversityEntity>) universityRepository.findAll();
        Assertions.assertEquals(universityList.size(), 3);
        Assertions.assertTrue(universityList.containsAll(universities));
    }
}
