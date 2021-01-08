package org.levelup.universities.hibernate.repository;

import org.levelup.universities.hibernate.domain.StudentEntity;
import org.levelup.universities.hibernate.domain.StudentGroupEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface StudentRepository {
    StudentEntity createStudent(Integer studentCard,String lastName, String firstName, Date birthday,StudentGroupEntity  studentGroup);
    StudentEntity findByStudentCard( Integer studentCard);
    Collection<StudentEntity> findByLastName(String lastName);
    Collection<StudentEntity> findAll();
    void deleteByStudentCard ( Integer studentCard);
}
