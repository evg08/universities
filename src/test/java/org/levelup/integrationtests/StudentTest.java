package org.levelup.integrationtests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.config.HibernateConfigurationTest;
import org.levelup.universities.hibernate.domain.*;
import org.levelup.universities.hibernate.repository.*;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StudentTest {
    private static SessionFactory factory;
    private UniversityRepository universityRepository;
    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;
    private StudentGroupRepository studentGroupRepository;
    private StudentRepository studentRepository;
    String name, shortName, facultyName, departmentName, secondDepartmentName, groupKey, secondGroupKey;
    int foundationYear, facultyId, studentCard, secondStudentCard;
    String lastName, firstName;
    Date birthday;

    public StudentTest() {
        factory = HibernateConfigurationTest.getFactory();
        universityRepository = new HibernateUniversityRepository(factory);
        facultyRepository = new HibernateFacultyRepository(factory);
        departmentRepository = new HibernateDepartmentRepository(factory);
        studentGroupRepository = new HibernateStudentGroupRepositoryRepository(factory);
        studentRepository = new HibernateStudentRepository(factory);
        name = "СПБ университет";
        shortName = "Спбгу";
        foundationYear = 1979;
        facultyId = 1;
        facultyName = "FTK";
        departmentName = "department";
        secondDepartmentName = "department2";
        groupKey = "groupKey1";
        secondGroupKey = "groupKey2";
        studentCard = 1;
        secondStudentCard = 2;
        lastName = "Ivanov";
        firstName = "Ivan";
        birthday = Date.valueOf("1997-08-14");


    }

    @BeforeEach
    public void cleanDataFromDatabase() {
        try (Session s = factory.openSession()) {
            Transaction tx = s.beginTransaction();
            String grQuery = "delete from StudentGroupEntity";
            String stQuery = "delete from StudentEntity";
            s.createQuery(stQuery).executeUpdate();
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
    public void testCreateStudent_thenReturnStudentEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentEntity studentEntity = studentRepository.createStudent(studentCard, lastName, firstName, birthday, groupEntity);
        Assertions.assertEquals(studentEntity.getStudentCard(), studentCard);
        Assertions.assertEquals(studentEntity.getLastName(), lastName);
        Assertions.assertEquals(studentEntity.getFirstName(), firstName);
        Assertions.assertEquals(studentEntity.getBirthday(), birthday);
        Assertions.assertEquals(studentEntity.getStudentGroup().getGroupKey(), groupEntity.getGroupKey());
    }

    @Test
    public void testFindByStudentCard_whenStudentCardIsExist_thenReturnStudentCardEntity() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentEntity studentEntity = studentRepository.createStudent(studentCard, lastName, firstName, birthday, groupEntity);

        StudentEntity searchedStudentEntity = studentRepository.findByStudentCard(studentCard);
        Assertions.assertEquals(searchedStudentEntity.getStudentCard(), studentEntity.getStudentCard());
        Assertions.assertEquals(searchedStudentEntity.getLastName(), studentEntity.getLastName());
        Assertions.assertEquals(searchedStudentEntity.getFirstName(), studentEntity.getFirstName());
        Assertions.assertEquals(searchedStudentEntity.getBirthday(), studentEntity.getBirthday());
        Assertions.assertEquals(searchedStudentEntity.getStudentGroup().getGroupKey(), studentEntity.getStudentGroup().getGroupKey());
    }

    @Test
    public void testFindByLastName_whenLastNamesAreExist_thenReturnListStudentEntities() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentEntity studentEntity = studentRepository.createStudent(studentCard, lastName, firstName, birthday, groupEntity);
        StudentEntity secondStudentEntity = studentRepository.createStudent(secondStudentCard, lastName, firstName, birthday, groupEntity);

        List<StudentEntity> studentList = new ArrayList<>();
        studentList.add(studentEntity);
        studentList.add(secondStudentEntity);
        List<StudentEntity> actualStudentList = (List<StudentEntity>) studentRepository.findByLastName(lastName);
        Assertions.assertEquals(actualStudentList.size(), 2);
        Assertions.assertTrue(actualStudentList.containsAll(studentList));
    }

    @Test
    public void testFindAll_whenStudentCardIsExist_thenReturnListStudentEntities() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentEntity studentEntity = studentRepository.createStudent(studentCard, lastName, firstName, birthday, groupEntity);
        StudentEntity secondStudentEntity = studentRepository.createStudent(secondStudentCard, lastName, firstName, birthday, groupEntity);

        List<StudentEntity> studentList = new ArrayList<>();
        studentList.add(studentEntity);
        studentList.add(secondStudentEntity);
        List<StudentEntity> actualStudentList = (List<StudentEntity>) studentRepository.findAll();
        Assertions.assertEquals(actualStudentList.size(), 2);
        Assertions.assertTrue(actualStudentList.containsAll(studentList));
    }

    @Test
    public void testDeleteByGroupKey_whenDataIsExist_thenDeleteStudentGroup() {
        UniversityEntity university = universityRepository.createUniversity(name, shortName, foundationYear);
        FacultyEntity faculty = facultyRepository.createFaculty(university.getId(), facultyId, facultyName);
        DepartmentEntity department = departmentRepository.createDepartment(departmentName, faculty);
        StudentGroupEntity groupEntity = studentGroupRepository.createStudentGroup(groupKey, department);
        StudentEntity studentEntity = studentRepository.createStudent(studentCard, lastName, firstName, birthday, groupEntity);
        studentRepository.deleteByStudentCard(studentEntity.getStudentCard());
        Assertions.assertThrows(NoResultException.class, () -> {
            studentRepository.findByStudentCard(studentEntity.getStudentCard());
        });
    }
}
