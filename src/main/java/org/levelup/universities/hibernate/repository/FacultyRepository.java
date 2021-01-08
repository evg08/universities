package org.levelup.universities.hibernate.repository;
//1 Controller Layer-controllers
//2 Service layer Services
//3 DAO Repositories

//FacultyDAO
//DAO-Data Access Object объект доступа к данным
//classical dao:
//  -CRUD operations :create read(readAll,readByID),update,delete
// -no methods like findBy<FIELD>,check<smth>
//Repository -DAO + методы поиска /проверки и другие (find,check,etc)

import org.levelup.universities.hibernate.domain.FacultyEntity;

public interface FacultyRepository {
    FacultyEntity createFaculty(Integer universityId,Integer facultyId,String name);
    FacultyEntity getById(Integer facultyId);
    FacultyEntity loadById(Integer facultyId);
}
