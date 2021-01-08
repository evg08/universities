package org.levelup.universities.hibernate.repository;

import java.util.List;

public interface FacultySubjectRepository {
    void weaveSubjectAndFaculty(Integer subjectId,Integer facultyId);
    void weaveSubjectAndFaculty(Integer subjectId, List<Integer> facultyIds);
    void weaveSubjectAndFaculty(List<Integer> subjectIds,Integer facultyId);
}
