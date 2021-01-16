package org.levelup.integrationtests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.config.HibernateConfigurationTest;
import org.levelup.universities.hibernate.domain.DepartmentEntity;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentTest {
    private static SessionFactory factory;
    private UniversityRepository universityRepository;
    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;
    String name, shortName, facultyName,departmentName, secondDepartmentName;
    int foundationYear, facultyId;

    public  DepartmentTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        facultyRepository = new HibernateFacultyRepository(factory);
        departmentRepository=new HibernateDepartmentRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        facultyId = 1;
        facultyName = "FTK";
        departmentName="department";
        secondDepartmentName="department2";

    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String depQuery = "delete from DepartmentEntity";
            s.createQuery(depQuery).executeUpdate();
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
    public void testCreateDepartment_thenReturnDepartmentEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department=departmentRepository.createDepartment(departmentName,faculty);
        Assertions.assertEquals(department.getName(), departmentName);
        Assertions.assertEquals(department.getFaculty(),faculty);
    }

    @Test
    public void testFindById_whenIdIsExist_thenReturnDepartmentEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity createdDepartment=departmentRepository.createDepartment(departmentName,faculty);
        DepartmentEntity department=departmentRepository.findById(createdDepartment.getId());
        Assertions.assertEquals(department.getName(), createdDepartment.getName());
        Assertions.assertEquals(department.getFaculty().getFacultyId(),createdDepartment.getFaculty().getFacultyId());
        Assertions.assertEquals(department.getFaculty().getName(),createdDepartment.getFaculty().getName());
           }

    @Test
    public void testFindAll_whenDataAreExist_thenReturnListDepartmentEntities() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity firstDepartment=departmentRepository.createDepartment(departmentName,faculty);
        DepartmentEntity secondDepartment=departmentRepository.createDepartment(secondDepartmentName,faculty);
        List<DepartmentEntity> departmentList=new ArrayList<>();
        departmentList.add(firstDepartment);
        departmentList.add(secondDepartment);
        List<DepartmentEntity> actualDepartmentList= (List<DepartmentEntity>) departmentRepository.findAll();
        Assertions.assertEquals(actualDepartmentList.size(), 2);
        Assertions.assertTrue(actualDepartmentList.containsAll(departmentList));

    }

    @Test
    public void testDeleteById_whenDataIsExist_thenDeleteDepartmentEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity createdDepartment=departmentRepository.createDepartment(departmentName,faculty);
        departmentRepository.deleteById(createdDepartment.getId());
        Assertions.assertThrows(NoResultException.class, () -> {
            departmentRepository.findById(createdDepartment.getId());
        });
    }

}
