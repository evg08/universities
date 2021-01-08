package org.levelup.universities.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.BiFunction;
import java.util.function.Function;


@RequiredArgsConstructor
public abstract class AbstractRepository {

    protected final SessionFactory factory;

    //runWithTransaction
    //runWithoutTransaction


    //Function<T,R>
    //I-incoming
    //R-outgoing
    protected <T> T runWithTransaction(Function<Session, T> function) { //BiFunction interface for 2 parameters
        Transaction tx = null;
        try (Session s = factory.openSession()) {
            tx = s.beginTransaction();

            T result = function.apply(s);

            tx.commit();
            return result;
        } catch (Exception exc) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(exc);
        }
    }

    //BiFunction<T,U,R>
//    T - the type of the first argument to the function
//    U - the type of the second argument to the function
//    R - the type of the result of the function
    protected <T> T runWithTransaction(BiFunction<Session, Object, T> biFunction) { //BiFunction interface for 2 parameters
        Transaction tx = null;
        try (Session s = factory.openSession()) {
            tx = s.beginTransaction();
            Object obj = new Object();

            T result = biFunction.apply(s, obj);

            tx.commit();
            return result;
        } catch (Exception exc) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(exc);
        }
    }


    //метод параметризирован
    protected <T> T run(SessionExecutor<T> executor) {
        //executor.execute();
        try (Session s = factory.openSession()) {
            return executor.execute(s);
        }

    }

}

