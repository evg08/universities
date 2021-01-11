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
import org.levelup.universities.hibernate.domain.StudentGroupEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class StudentGroupTest {
    private static SessionFactory factory;
    private UniversityRepository universityRepository;
    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;
    private StudentGroupRepository studentGroupRepository;
    String name, shortName, facultyName, departmentName, secondDepartmentName, groupKey, secondGroupKey;
    int foundationYear, facultyId;

    public StudentGroupTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        facultyRepository = new HibernateFacultyRepository(factory);
        departmentRepository = new HibernateDepartmentRepository(factory);
        studentGroupRepository = new HibernateStudentGroupRepositoryRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        facultyId = 1;
        facultyName = "FTK";
        departmentName = "department";
        secondDepartmentName = "department2";
        groupKey = "groupKey1";
        secondGroupKey = "groupKey2";
    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String grQuery = "delete from StudentGroupEntity";
            s.createQuery(grQuery).executeUpdate();
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
    public void testCreateStudentGroup_thenReturnStudentGroupEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        Assertions.assertEquals(groupEntity.getGroupKey(), groupKey);
        Assertions.assertEquals(groupEntity.getDepartment().getId(), department.getId());
    }

    @Test
    public void testFindByGroupKey_whenGroupKeyIsExist_thenReturnStudentGroupEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentGroupEntity searchedGroupEntity = studentGroupRepository.findByGroupKey(groupKey);
        Assertions.assertEquals(groupEntity.getGroupKey(), searchedGroupEntity.getGroupKey());
        Assertions.assertEquals(groupEntity.getDepartment().getId(), searchedGroupEntity.getDepartment().getId());
    }

    @Test
    public void testFindAll_whenDataAreExist_thenReturnListStudentGroupEntities() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentGroupEntity secondGroupEntity = studentGroupRepository.createStudentGroup(secondGroupKey, department);
        List<StudentGroupEntity> groupList = new ArrayList<>();
        groupList.add(groupEntity);
        groupList.add(secondGroupEntity);
        List<StudentGroupEntity> actualGroupList = (List<StudentGroupEntity>) studentGroupRepository.findAll();
        Assertions.assertEquals(actualGroupList.size(), 2);
        Assertions.assertTrue(actualGroupList.containsAll(groupList));
    }

    @Test
    public void testDeleteByGroupKey_whenDataIsExist_thenDeleteStudentGroup() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        studentGroupRepository.createStudentGroup(groupKey, department);
        studentGroupRepository.deleteByGroupKey(groupKey);
        Assertions.assertThrows(NoResultException.class, () -> {
            studentGroupRepository.findByGroupKey(groupKey);
        });
    }
}
