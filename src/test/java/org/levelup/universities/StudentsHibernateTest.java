package org.levelup.universities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.domain.*;
import org.levelup.universities.hibernate.repository.HibernateStudentRepository;
import org.levelup.universities.hibernate.repository.StudentRepository;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StudentsHibernateTest {
    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Query query;

    // private DepartmentRepository departmentRepository;
    // private UniversityRepository universityRepository;
    // private FacultyRepository facultyRepository;
    // private StudentGroupRepository studentGroupRepository;
    private StudentRepository studentRepository;
    private UniversityEntity university;
    private FacultyEntity faculty;
    private DepartmentEntity department;
    private StudentGroupEntity studentGroup;

    @BeforeEach
    public void setupSession() {
        MockitoAnnotations.openMocks(this);
        //  departmentRepository = new HibernateDepartmentRepository(factory);
        //  universityRepository = new HibernateUniversityRepository(factory);
        //  facultyRepository = new HibernateFacultyRepository(factory);
        // studentGroupRepository = new HibernateStudentGroupRepositoryRepository(factory);
        studentRepository = new HibernateStudentRepository(factory);
        Mockito.when(factory.openSession()).thenReturn(session);

        university = new UniversityEntity("Saint Petersburg University", "SPbGU", 1900);

        faculty = new FacultyEntity();
        faculty.setName("FTK");
        faculty.setFacultyId(1);
        faculty.setUniversity(university);
        university.setFaculties(Collections.singletonList(faculty));
        department = new DepartmentEntity("department", faculty);
        studentGroup = new StudentGroupEntity("group", department);

    }

    @Test
    public void testCreateStudent_whenInputDataIsValid_thenReturnStudentEntity() {
        Integer studentCard = 1;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");

        StudentEntity expectedStudent = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        StudentEntity actualStudent = studentRepository.createStudent(studentCard, lastName, firstName, birthday, studentGroup);
        Assertions.assertEquals(expectedStudent, actualStudent, "Expected and Actual student groups are different");
    }

    @Test
    public void testFindByStudentCard_whenStudentCardIsExist_thenReturnStudentsEntity() {
        Integer studentCard = 1;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity expectedStudent = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);

        Mockito.when(session.createQuery("from StudentEntity where studentCard=:studentCard", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("studentCard", studentCard)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(expectedStudent);

        StudentEntity actualStudent = studentRepository.findByStudentCard(studentCard);
        Assertions.assertEquals(actualStudent, expectedStudent, "Expected and Actual students are different");
    }

    @Test
    public void testFindByStudentCard_whenStudentCardIsNotExist_thenReturnNoResultException() {
        Integer studentCard = 1;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity student = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);

        Mockito.when(session.createQuery("from StudentEntity where studentCard=:studentCard", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("studentCard", studentCard)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            studentRepository.findByStudentCard(studentCard);
        });
    }

    @Test
    public void testFindByLastName_whenLastNameIsExist_thenReturnStudentsEntity() {
        Integer studentCard = 1;
        Integer studentCardSecond = 2;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity expectedStudent1 = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        StudentEntity expectedStudent2 = new StudentEntity(studentCardSecond, lastName, firstName, birthday, studentGroup);
        List<StudentEntity> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);

        Mockito.when(session.createQuery("from StudentEntity where lastName=:lastName", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("lastName", lastName)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(expectedStudents);

        Collection<StudentEntity> actualStudents = studentRepository.findByLastName(lastName);
        Assertions.assertEquals(actualStudents, expectedStudents, "Expected and Actual students are different");
    }

    @Test
    public void testFindByLastName_whenLastNameIsNotExist_thenReturnNoResultException() {
        Integer studentCard = 1;
        Integer studentCardSecond = 2;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity expectedStudent1 = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        StudentEntity expectedStudent2 = new StudentEntity(studentCardSecond, lastName, firstName, birthday, studentGroup);
        List<StudentEntity> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);

        Mockito.when(session.createQuery("from StudentEntity where lastName=:lastName", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("lastName", lastName)).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);

        Assertions.assertThrows(NoResultException.class, () -> {
            studentRepository.findByLastName(lastName);
        });
    }


    @Test
    public void testFindByAll_whenStudentsAreExist_thenReturnCollectionOfStudentsEntity() {
        Integer studentCard = 1;
        Integer studentCardSecond = 2;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity expectedStudent1 = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        StudentEntity expectedStudent2 = new StudentEntity(studentCardSecond, lastName, firstName, birthday, studentGroup);
        List<StudentEntity> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);

        Mockito.when(session.createQuery("from StudentEntity", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(expectedStudents);

        Collection<StudentEntity> actualStudents = studentRepository.findAll();
        Assertions.assertEquals(actualStudents, expectedStudents, "Expected and Actual students are different");

    }

    @Test
    public void testFindByAll_whenStudentsAreNotPresent_thenReturnNoResultException() {
        Integer studentCard = 1;
        Integer studentCardSecond = 2;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity expectedStudent1 = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        StudentEntity expectedStudent2 = new StudentEntity(studentCardSecond, lastName, firstName, birthday, studentGroup);
        List<StudentEntity> expectedStudents = new ArrayList<>();
        expectedStudents.add(expectedStudent1);
        expectedStudents.add(expectedStudent2);

        Mockito.when(session.createQuery("from StudentEntity", StudentEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);

        Assertions.assertThrows(NoResultException.class, () -> {
            studentRepository.findAll();
        });
    }

    //  @Test
    public void testDeleteByStudentCard_whenStudentIsExist_thenStudentEntityWasRemoved() {
        Integer studentCard = 1;
        String lastName = "Ivanov";
        String firstName = "Ivan";
        Date birthday = Date.valueOf("1997-08-14");
        StudentEntity student = new StudentEntity(studentCard, lastName, firstName, birthday, studentGroup);
        Mockito.when(session.get(StudentEntity.class, studentCard)).thenReturn(student);
        studentRepository.deleteByStudentCard(studentCard);
        Mockito.verify(studentRepository, Mockito.times(1)).deleteByStudentCard(studentCard);

    }

    @Test
    public void testDeleteByStudentCard_whenStudentIsNotExist_thenReturnRuntimeException() {
        Integer studentCard = 1;

        Mockito.when(session.get(StudentEntity.class, studentCard)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> {
            studentRepository.deleteByStudentCard(studentCard);
        });
    }

}
