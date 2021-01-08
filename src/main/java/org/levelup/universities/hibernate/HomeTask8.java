package org.levelup.universities.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.config.HibernateСonfiguration;
import org.levelup.universities.hibernate.domain.*;
import org.levelup.universities.hibernate.repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class HomeTask8 {
    public static void main(String[] args) {
        SessionFactory factory = HibernateСonfiguration.getFactory();


        FacultyRepository facultyRepository =new HibernateFacultyRepository(factory);
        SubjectRepository subjectRepository=new HibernateSubjectRepository(factory);
        FacultySubjectRepository fsRepository=new HibernateFacultySubjectRepository(factory);
        HibernateDepartmentRepository departmentRepository = new HibernateDepartmentRepository(factory);
        HibernateStudentGroupRepositoryRepository studentGroupRepository=new HibernateStudentGroupRepositoryRepository(factory);
        StudentRepository studentRepository= new HibernateStudentRepository(factory);
/*
        //---task 1
        FacultyEntity faculty3= facultyRepository.createFaculty(103,533,"Инженерный533");
        FacultyEntity faculty4= facultyRepository.createFaculty(104,534,"Инженерный534");
        List<Integer> facultyListIds= new ArrayList<>();
        facultyListIds.add(faculty3.getFacultyId());
        facultyListIds.add(faculty4.getFacultyId());

        List<Integer> subjListIds= new ArrayList<>();
        subjListIds.add(subjectRepository.createSubject(7, "Экономика7", 72).getId());
        subjListIds.add(subjectRepository.createSubject(8, "Физика8", 72).getId());


        FacultyEntity faculty5= facultyRepository.createFaculty(105,535,"Инженерный535");
        SubjectEntity subj = subjectRepository.createSubject(6, "Высшая математика4", 300);


        fsRepository.weaveSubjectAndFaculty(subj.getId(),facultyListIds);
        fsRepository.weaveSubjectAndFaculty(subjListIds,faculty5.getFacultyId());
*/
        //---task 1

        //HibernateFacultyRepository facultyRepository= new HibernateFacultyRepository(factory);

    /*    FacultyEntity faculty = facultyRepository.getById(233);*/
         DepartmentEntity departmentEntity=departmentRepository.findById(4);
   /*      System.out.println(departmentEntity.getName()+"  "+departmentEntity.getId());
        System.out.println("------------------>");

        DepartmentEntity departmentEntityNew = departmentRepository.createDepartment("Кафедра химии2",faculty);
        System.out.println(departmentEntityNew.getId()+"  "+departmentEntityNew.getName());*/

      //  HibernateStudentGroupRepositoryRepository studentGroupRepository=new HibernateStudentGroupRepositoryRepository(factory);
      //  StudentRepository studentRepository= new HibernateStudentRepository(factory);
        StudentGroupEntity studentGroup= studentGroupRepository.createStudentGroup("test2",departmentEntity);
    //    System.out.println(studentGroup.getGroupKey());
        System.out.println("------------------>");

       System.out.println("------------------>");
        StudentGroupEntity findStudentGroup=studentGroupRepository.findByGroupKey("test2");//

        System.out.println(findStudentGroup.getGroupKey());
        System.out.println("------------------>");

        Date date= Date.valueOf("1997-08-14");

       StudentEntity student= studentRepository.createStudent(1,"Ivanov","Ivan",date,findStudentGroup);
      //  System.out.println(student.getStudentCard()+"  " +student.getLastName());
        System.out.println("------------------>");

        StudentEntity byStudentCard = studentRepository.findByStudentCard(1);
        System.out.println(byStudentCard.getStudentCard()+"  " +byStudentCard.getLastName());
        System.out.println("------------------>");

   //     List<StudentEntity> students = (List<StudentEntity>) studentRepository.findByLastName("Ivanov");
   //     students.stream().forEach(u -> System.out.println(u.getStudentCard() + " " + u.getLastName()));
       //  departmentRepository.deleteById(3);
      //  studentRepository.deleteByStudentCard(1);
      //  studentGroupRepository.deleteByGroupKey("test2");

        factory.close();


    }
}
