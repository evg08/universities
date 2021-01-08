package org.levelup.universities.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.config.HibernateСonfiguration;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.HibernateUniversityRepository;
import org.levelup.universities.hibernate.repository.UniversityRepository;

import java.util.List;

public class App {
    public static void main(String[] args) {
        SessionFactory factory = HibernateСonfiguration.getFactory();
        UniversityRepository universityRepository = new HibernateUniversityRepository(factory);
        //  UniversityEntity university = universityRepository.createUniversity("Высшая ЛПУ57", "ЛПУ57", 1949);
        //  UniversityEntity university = universityRepository.createUniversity("Высшая ЛПУ2", "ЛПУ2", 1944,2454,"Инженерный");

//        System.out.println("ID:"+university);

        //  UniversityEntity university2=universityRepository.findById(100);
        //    System.out.println(university2.getName()+ " "+ university2.getShortName()+" "+university2.getFoundationYear());

     //   List<UniversityEntity> universityAll = (List<UniversityEntity>) universityRepository.findAll();
     //   universityAll.stream().forEach(u -> System.out.println(u.getName() + " " + u.getShortName() + " " + u.getFoundationYear()));

        UniversityEntity universityName = universityRepository.findByName("Высшая ЛПУ2");
     //   System.out.println(universityName.getName() + " " + universityName.getShortName() + " " + universityName.getFoundationYear());
       System.out.println(universityName.toString());
        /*
        UniversityEntity universityShortName = universityRepository.findByShortName("ЛПУ2");
        System.out.println(universityShortName.getName() + " " + universityShortName.getShortName() + " " + universityShortName.getFoundationYear());

        List<UniversityEntity> universityYearList = (List<UniversityEntity>) universityRepository.findByFoundationYear(1944);
        universityYearList.stream().forEach(u -> System.out.println(u.getName() + " " + u.getShortName() + " " + u.getFoundationYear()));

        List<UniversityEntity> universityYearList2 = (List<UniversityEntity>) universityRepository.findAllFoundedUniversitiesBetweenYears(1903, 1949);
        universityYearList2.stream().forEach(u -> System.out.println(u.getName() + " " + u.getShortName() + " " + u.getFoundationYear()));
*/




        factory.close();
    }
}
