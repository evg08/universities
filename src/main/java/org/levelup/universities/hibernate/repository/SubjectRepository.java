package org.levelup.universities.hibernate.repository;

import org.levelup.universities.hibernate.domain.SubjectEntity;

public interface SubjectRepository {
    SubjectEntity createSubject(Integer id,String subject,int hours);
    SubjectEntity findById(Integer subjectId);
    void removeSubject(Integer subjectId);
}
