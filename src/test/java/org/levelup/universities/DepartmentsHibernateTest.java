package org.levelup.universities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.domain.*;
import org.levelup.universities.hibernate.repository.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.mock;

public class DepartmentsHibernateTest {
    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Query query;

    private DepartmentRepository departmentRepository;
 //   private UniversityRepository universityRepository;
   // private FacultyRepository facultyRepository;
    private UniversityEntity university;
    private FacultyEntity faculty;


    @BeforeEach
    public void setupSession() {
        MockitoAnnotations.openMocks(this);
        departmentRepository = new HibernateDepartmentRepository(factory);
      //  universityRepository = new HibernateUniversityRepository(factory);
       // facultyRepository=new HibernateFacultyRepository(factory);
        Mockito.when(factory.openSession()).thenReturn(session);


        university= new UniversityEntity("Saint Petersburg University","SPbGU",1900);

        faculty=new FacultyEntity();
        faculty.setName("FTK");
        faculty.setFacultyId(1);
        faculty.setUniversity(university);
        university.setFaculties(Collections.singletonList(faculty));



    }

    //@Test
    public void testCreateDepartment_whenInputDataIsValid_thenReturnDepartmentEntity() {

        String departmentName="department";
        DepartmentEntity department =new DepartmentEntity(departmentName,faculty);
        DepartmentEntity actualDepartment= departmentRepository.createDepartment(departmentName,faculty);
        Assertions.assertEquals(department, actualDepartment,"Expected and Actual departments are different");
    }


    @Test
    public void testFindById_whenIdIsExist_thenReturnDepartmentEntity(){
        String departmentName="department";
        DepartmentEntity department =new DepartmentEntity(departmentName,faculty);
        int id=1;
        department.setId(id);
        Mockito.when(session.createQuery("from DepartmentEntity where id=:id", DepartmentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("id", id)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn( department);

        DepartmentEntity actualDepartment = departmentRepository.findById(id);
        Assertions.assertEquals(actualDepartment, department);
    }

   // @Test
    public void testFindById_whenIdIsNotExist_thenReturnNoResultException(){

        int id=1;
        Mockito.when(session.createQuery("from DepartmentEntity where id=:id",DepartmentEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("id", id)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            departmentRepository.findById(id);
        });
    }

    @Test
    public void testFindByAll_whenDepartmentsAreExist_thenReturnCollectionOfDepartmentEntity(){
        String departmentName1="department1";
        DepartmentEntity department1 =new DepartmentEntity(departmentName1,faculty);
        String departmentName2="department2";
        DepartmentEntity department2 =new DepartmentEntity(departmentName2,faculty);

        department1.setId(1);
        department2.setId(2);

        List< DepartmentEntity> expectedDepartments = new ArrayList<>();
        expectedDepartments.add(department1);
        expectedDepartments.add(department2);
        Mockito.when(session.createQuery("from DepartmentEntity", DepartmentEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(expectedDepartments);

        Collection<DepartmentEntity>actualDepartments  = departmentRepository.findAll();
        Assertions.assertEquals(actualDepartments, expectedDepartments);
    }

    @Test
    public void testFindByAll_whenDepartmentsAreNotPresent_thenReturnNoResultException(){

        Mockito.when(session.createQuery("from DepartmentEntity", DepartmentEntity.class))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            departmentRepository.findAll();
        });
    }

  //  @Test
    public void  testDeleteByIdwhenIdIsExistd_thenDepartmentEntityWasRemoved() throws NoSuchMethodException {
        String departmentName="department";
        DepartmentEntity department =new DepartmentEntity(departmentName,faculty);
        int id=1;
        AbstractRepository d = mock(AbstractRepository.class);

      //  doNothing().when(d.getClass()).getDeclaredMethod("runWithTransaction",Function.class);

        Mockito.when(AbstractRepository.class.getDeclaredMethod("runWithTransaction",Function.class)).thenReturn(null);



        System.out.println(AbstractRepository.class.getDeclaredMethod("runWithTransaction",Function.class));
       // System.out.println(HibernateDepartmentRepository.class.getDeclaredMethod("deleteById",int.class));

        Mockito.when(session.get(DepartmentEntity.class,id)).thenReturn(department);


        departmentRepository.deleteById(id);
        Mockito.verify(departmentRepository,Mockito.times(1)).deleteById(id);

    }

     @Test
    public void  testDeleteById_whenIdIsNotExistd_thenReturnRuntimeException(){
         int id=1;
         Mockito.when(session.get(DepartmentEntity.class,id)).thenThrow(RuntimeException.class);
         Assertions.assertThrows(RuntimeException.class, () -> {
             departmentRepository.deleteById(id);
         });
     }

}
