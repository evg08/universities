package org.levelup.universities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.domain.DepartmentEntity;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.StudentGroupEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.HibernateStudentGroupRepositoryRepository;
import org.levelup.universities.hibernate.repository.StudentGroupRepository;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StudentGroupsHibernateTest {
    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Query query;

    // private DepartmentRepository departmentRepository;
    // private UniversityRepository universityRepository;
    // private FacultyRepository facultyRepository;
    private StudentGroupRepository studentGroupRepository;
    private UniversityEntity university;
    private FacultyEntity faculty;
    private DepartmentEntity department;

    @BeforeEach
    public void setupSession() {
        MockitoAnnotations.openMocks(this);
        //  departmentRepository = new HibernateDepartmentRepository(factory);
        //  universityRepository = new HibernateUniversityRepository(factory);
        //  facultyRepository = new HibernateFacultyRepository(factory);
        studentGroupRepository = new HibernateStudentGroupRepositoryRepository(factory);
        Mockito.when(factory.openSession()).thenReturn(session);

        university = new UniversityEntity("Saint Petersburg University", "SPbGU", 1900);

        faculty = new FacultyEntity();
        faculty.setName("FTK");
        faculty.setFacultyId(1);
        faculty.setUniversity(university);
        university.setFaculties(Collections.singletonList(faculty));
        department = new DepartmentEntity("department", faculty);


    }

    //  @Test
    public void testCreateStudentsGroup_whenInputDataIsValid_thenReturnStudentsGroupEntity() {

        String groupKey = "groupKey";
        StudentGroupEntity expectedStudentGroup = new StudentGroupEntity(groupKey, department);
        StudentGroupEntity actualStudentGroup = studentGroupRepository.createStudentGroup(groupKey, department);
        Assertions.assertEquals(expectedStudentGroup, actualStudentGroup, "Expected and Actual student groups are different");
    }

    @Test
    public void testFindByGroupKey_whenGroupKeyIsExist_thenReturnStudentsGroupEntity() {
        String groupKey = "groupKey";
        StudentGroupEntity expectedStudentGroup = new StudentGroupEntity(groupKey, department);

        Mockito.when(session.createQuery("from StudentGroupEntity where groupKey=:groupKey", StudentGroupEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("groupKey", groupKey)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(expectedStudentGroup);

        StudentGroupEntity actualStudentGroup = studentGroupRepository.findByGroupKey(groupKey);
        Assertions.assertEquals(actualStudentGroup, expectedStudentGroup, "Expected and Actual student groups are different");
    }

    @Test
    public void testFindByGroupKey_whenGroupKeyIsNotExist_thenReturnNoResultException() {
        String groupKey = "groupKey";
        Mockito.when(session.createQuery("from StudentGroupEntity where groupKey=:groupKey", StudentGroupEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("groupKey", groupKey)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            studentGroupRepository.findByGroupKey(groupKey);
        });
    }

    @Test
    public void testFindByAll_whenGroupIsExist_thenReturnCollectionOfStudentsGroupEntity() {
        String groupKey1 = "groupKey1";
        String groupKey2 = "groupKey1";
        StudentGroupEntity expectedStudentGroup1 = new StudentGroupEntity(groupKey1, department);
        StudentGroupEntity expectedStudentGroup2 = new StudentGroupEntity(groupKey2, department);
        List<StudentGroupEntity> expectedStudentGroups = new ArrayList<>();
        expectedStudentGroups.add(expectedStudentGroup1);
        expectedStudentGroups.add(expectedStudentGroup2);

        Mockito.when(session.createQuery("from StudentGroupEntity", StudentGroupEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(expectedStudentGroups);
        Collection<StudentGroupEntity> actualStudentGroups = studentGroupRepository.findAll();
        Assertions.assertEquals(actualStudentGroups, expectedStudentGroups, "Expected and Actual student groups are different");
    }

    @Test
    public void testFindByAll_whenGroupAreNotExist_thenReturnNoResultException() {

        Mockito.when(session.createQuery("from StudentGroupEntity", StudentGroupEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            studentGroupRepository.findAll();
        });
    }

    //  @Test
    public void testDeleteByGroupKeywhenIdIsExistd_thenGroupEntityWasRemoved() {
        String groupKey = "groupKey";
        StudentGroupEntity studentGroup = new StudentGroupEntity(groupKey, department);
        Mockito.when(session.get(StudentGroupEntity.class, groupKey)).thenReturn(studentGroup);
        studentGroupRepository.deleteByGroupKey(groupKey);
        Mockito.verify(studentGroupRepository, Mockito.times(1)).deleteByGroupKey(groupKey);

    }

    @Test
    public void testDeleteByGroupKeywhenIdIsNotExistd_thenReturnRuntimeException() {
        String groupKey = "groupKey";
        Mockito.when(session.get(StudentGroupEntity.class, groupKey)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> {
            studentGroupRepository.deleteByGroupKey(groupKey);
        });
    }
}
