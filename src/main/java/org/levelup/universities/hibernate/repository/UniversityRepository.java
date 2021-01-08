package org.levelup.universities.hibernate.repository;

import org.levelup.universities.hibernate.domain.UniversityEntity;

import java.util.Collection;

public interface UniversityRepository {
    UniversityEntity createUniversity(String name, String shortName, int foundationYear);

    UniversityEntity createUniversity(String name, String shortName, int foundationYear, int facultyId, String facultyName);

    UniversityEntity findById(int universityId);

    UniversityEntity findByName(String name);

    UniversityEntity findByShortName(String shortName);

    Collection<UniversityEntity> findByFoundationYear(int foundationYear);

    Collection<UniversityEntity> findAllFoundedUniversitiesBetweenYears(int foundationYearStart, int foundationYearEnd);

    Collection<UniversityEntity> findAll();

}

/*
create table university (
	-- text
	id int primary key,
	name varchar not null unique,
	short_name varchar(20) not null unique,
	foundation_year int not null
);
* */