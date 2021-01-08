package org.levelup.universities.hibernate.repository;

import org.levelup.universities.hibernate.domain.DepartmentEntity;
import org.levelup.universities.hibernate.domain.StudentGroupEntity;
import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.util.Collection;

public interface StudentGroupRepository {
    StudentGroupEntity createStudentGroup(String groupKey, DepartmentEntity department);
    StudentGroupEntity findByGroupKey(String groupKey);
    Collection<StudentGroupEntity> findAll();
    void deleteByGroupKey(String groupKey);
}
