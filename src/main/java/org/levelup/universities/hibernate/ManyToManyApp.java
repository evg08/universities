package org.levelup.universities.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.config.HibernateСonfiguration;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.SubjectEntity;
import org.levelup.universities.hibernate.repository.*;

import java.util.ArrayList;
import java.util.List;

public class ManyToManyApp {
    public static void main(String[] args) {
        SessionFactory factory= HibernateСonfiguration.getFactory();
        FacultyRepository facultyRepository =new HibernateFacultyRepository(factory);
        SubjectRepository subjectRepository=new HibernateSubjectRepository(factory);
        FacultySubjectRepository fsRepository=new HibernateFacultySubjectRepository(factory);
       /* FacultyEntity faculty= facultyRepository.createFaculty(102,234,"Инженерный");
        subjectRepository.createSubject(1, "Экономика", 72);
        subjectRepository.createSubject(2, "Физика", 72);
        subjectRepository.createSubject(3, "Высшая математика", 300);

        fsRepository.weaveSubjectAndFaculty(1, 234);
        fsRepository.weaveSubjectAndFaculty(2, 234);
        fsRepository.weaveSubjectAndFaculty(3, 234);*/

        //home task 8

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

        factory.close();

    }
}
