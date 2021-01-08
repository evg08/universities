package org.levelup.universities.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.universities.hibernate.domain.FacultyEntity;
import org.levelup.universities.hibernate.domain.SubjectEntity;

import java.util.List;

@RequiredArgsConstructor
public class HibernateFacultySubjectRepository implements FacultySubjectRepository {
    private final SessionFactory factory;

    @Override
    public void weaveSubjectAndFaculty(Integer subjectId, Integer facultyId) {
        try (Session s = factory.openSession()) {
            Transaction t = s.beginTransaction();
            SubjectEntity subject = s.get(SubjectEntity.class, subjectId);
            FacultyEntity faculty = s.get(FacultyEntity.class, facultyId);

            subject.getFaculties().add(faculty);
            faculty.getSubjects().add(subject);
            t.commit();
        }
    }

    @Override
    public void weaveSubjectAndFaculty(Integer subjectId, List<Integer> facultyIds) {
        try (Session s = factory.openSession()) {
            Transaction t = s.beginTransaction();
            SubjectEntity subject = s.get(SubjectEntity.class, subjectId);
            facultyIds.forEach(facultyId ->
            {
                FacultyEntity faculty = s.get(FacultyEntity.class, facultyId);
                faculty.getSubjects().add(subject);
                subject.getFaculties().add(faculty);

            });
            t.commit();
        }
    }

    @Override
    public void weaveSubjectAndFaculty(List<Integer> subjectIds, Integer facultyId) {
        try (Session s = factory.openSession()) {
            Transaction t = s.beginTransaction();
            FacultyEntity faculty = s.get(FacultyEntity.class, facultyId);
            subjectIds.forEach(subjectId ->
            {
                SubjectEntity subject = s.get(SubjectEntity.class, subjectId);
                faculty.getSubjects().add(subject);
                subject.getFaculties().add(faculty);

            });
            t.commit();
        }
    }
}
