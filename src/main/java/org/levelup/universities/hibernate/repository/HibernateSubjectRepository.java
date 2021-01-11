package org.levelup.universities.hibernate.repository;

import org.hibernate.SessionFactory;
import org.levelup.universities.hibernate.domain.SubjectEntity;

//@RequiredArgsConstructor
public class HibernateSubjectRepository extends AbstractRepository implements SubjectRepository {
    //private final SessionFactory factory;
    public HibernateSubjectRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public SubjectEntity createSubject(Integer id, String subject, int hours) {
  /*      try (Session s=factory.openSession()){
//            SessionImpl impl=(SessionImpl)s;
//            Connection connection=impl.connection();
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            Transaction t=s.beginTransaction();
            SubjectEntity subj =new SubjectEntity(id,subject,hours);
            s.persist(subj);
            t.commit();

            return subj;
        }*/
        return runWithTransaction(s -> {
            SubjectEntity subj = new SubjectEntity(id, subject, hours);
            s.persist(subj);
            return subj;
        });
    }

    @Override
    public SubjectEntity findById(Integer subjectId) {
        return run(s -> s.createQuery("from SubjectEntity where id=:subjectId", SubjectEntity.class)
                .setParameter("subjectId", subjectId)
                .getSingleResult()
        );
    }

    @Override
    public void removeSubject(Integer subjectId) {
        runWithTransaction(session -> {
                    SubjectEntity subject = session.get(SubjectEntity.class, subjectId);
                    session.remove(subject);
                    return null;
                }
        );
    }
}
