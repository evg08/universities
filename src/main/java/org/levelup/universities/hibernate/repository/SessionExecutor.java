package org.levelup.universities.hibernate.repository;

import org.hibernate.Session;

@FunctionalInterface
public interface SessionExecutor <T>{
     T execute(Session s);
}

