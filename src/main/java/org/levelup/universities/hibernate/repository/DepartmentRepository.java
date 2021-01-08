package org.levelup.universities.hibernate.repository;

import org.levelup.universities.hibernate.domain.DepartmentEntity;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.util.Collection;

public interface DepartmentRepository {
    DepartmentEntity createDepartment(String name, FacultyEntity faculty);
    DepartmentEntity findById(int id);
    Collection<DepartmentEntity> findAll();
    void deleteById (int id);
   }
