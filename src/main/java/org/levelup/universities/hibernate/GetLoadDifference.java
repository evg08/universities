package org.levelup.universities.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.config.HibernateСonfiguration;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.repository.FacultyRepository;
import org.levelup.universities.hibernate.repository.HibernateFacultyRepository;

public class GetLoadDifference {
    public static void main(String[] args) {
        SessionFactory factory = HibernateСonfiguration.getFactory();
        FacultyRepository facultyRepository =new HibernateFacultyRepository(factory);
        FacultyEntity faculty=facultyRepository.getById(2);
        System.out.println("getById "+faculty.getName());

        FacultyEntity loadedFaculty=facultyRepository.loadById(2);
        System.out.println("LoadById "+loadedFaculty.getFacultyId());
        factory.close();
    }
}
