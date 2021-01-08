package org.levelup.universities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.universities.hibernate.domain.UniversityEntity;
import org.levelup.universities.hibernate.repository.HibernateUniversityRepository;
import org.levelup.universities.hibernate.repository.UniversityRepository;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class UniversitiesHibernateTest {
    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Query query;

    private UniversityRepository universityRepository;


    @BeforeEach
    public void setupSession() {
        MockitoAnnotations.openMocks(this);
        universityRepository = new HibernateUniversityRepository(factory);
        Mockito.when(factory.openSession()).thenReturn(session);
    }

    @Test
    public void testFindByName_whenNameIsExist_thenReturnUniversityEntity() {
        UniversityEntity universityEntity = new UniversityEntity("СПБ Политехнический университет", "Спбгпу", 1978);
        Mockito.when(session.createQuery("from UniversityEntity where name=:name", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("name", universityEntity.getName())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(universityEntity);

        UniversityEntity universityName = universityRepository.findByName(universityEntity.getName());
        Assertions.assertEquals(universityEntity, universityName);
    }

    @Test
    public void testFindByName_whenNameIsNotExist_thenReturnNoResultException() {
        String name = "СПБ Государственный университет";
        Mockito.when(session.createQuery("from UniversityEntity where name=:name", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("name", name)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            universityRepository.findByName(name);
        });
    }

    @Test
    public void testFindByShortName_whenShortNameIsExist_thenReturnUniversityEntity() {
        UniversityEntity universityEntity = new UniversityEntity("СПБ Политехнический университет", "Спбгпу", 1978);
        Mockito.when(session.createQuery("from UniversityEntity where short_name=:shortName", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("shortName", universityEntity.getShortName())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(universityEntity);

        UniversityEntity universityShortName = universityRepository.findByShortName(universityEntity.getShortName());
        Assertions.assertEquals(universityEntity, universityShortName);
    }

    @Test
    public void testFindByShortName_whenShortNameIsNotExist_thenReturnNoResultException() {
        String shortName = "Спбгпу";
        Mockito.when(session.createQuery("from UniversityEntity where short_name=:shortName", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("shortName", shortName)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            universityRepository.findByShortName(shortName);
        });
    }

    @Test
    public void testFindByYear_whenYearIsExist_thenReturnListUniversityEntities() {
        List<UniversityEntity> universities = new ArrayList<>();
        universities.add(new UniversityEntity("СПБ Политехнический университет", "Спбгпу", 1978));
        universities.add(new UniversityEntity("СПБ Государственный университет", "Спбгу", 1978));

        Mockito.when(session.createQuery("from UniversityEntity where foundation_year=:foundationYear", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("foundationYear", universities.get(0).getFoundationYear())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(universities);

        List<UniversityEntity> universityYearList = (List<UniversityEntity>) universityRepository.findByFoundationYear(universities.get(0).getFoundationYear());
        Assertions.assertEquals(universityYearList.size(), universities.size());
        Assertions.assertTrue(universityYearList.containsAll(universities));
    }

    @Test
    public void testFindByYear_whenYearIsNotExist_thenReturnNoResultException() {
        int foundationYear = 1978;
        Mockito.when(session.createQuery("from UniversityEntity where foundation_year=:foundationYear", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("foundationYear", foundationYear)).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            universityRepository.findByFoundationYear(foundationYear);
        });
    }

    @Test
    public void testFindAllFoundedUniversitiesBetweenYears_whenYearsAreExist_thenReturnListUniversityEntities() {
        List<UniversityEntity> universities = new ArrayList<>();
        universities.add(new UniversityEntity("СПБ Политехнический университет", "Спбгпу", 1970));
        universities.add(new UniversityEntity("СПБ Государственный университет", "Спбгу", 1978));

        Mockito.when(session.createQuery("from UniversityEntity where foundation_year BETWEEN :foundationYearStart AND  :foundationYearEnd", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("foundationYearStart", universities.get(0).getFoundationYear())).thenReturn(query);
        Mockito.when(query.setParameter("foundationYearEnd", universities.get(1).getFoundationYear())).thenReturn(query);

        Mockito.when(query.getResultList()).thenReturn(universities);

        List<UniversityEntity> universityYearList = (List<UniversityEntity>) universityRepository.findAllFoundedUniversitiesBetweenYears(universities.get(0).getFoundationYear(), universities.get(1).getFoundationYear());
        Assertions.assertEquals(universityYearList.size(), universities.size());
        Assertions.assertTrue(universityYearList.containsAll(universities));
    }

    @Test
    public void testFindAllFoundedUniversitiesBetweenYears_whenYearsAreNotValid_thenReturnNoResultException() {
        int foundationYearStart = 1978;
        int foundationYearEnd = 1905;
        Mockito.when(session.createQuery("from UniversityEntity where foundation_year BETWEEN :foundationYearStart AND  :foundationYearEnd", UniversityEntity.class))
                .thenReturn(query);
        Mockito.when(query.setParameter("foundationYearStart", foundationYearStart)).thenReturn(query);
        Mockito.when(query.setParameter("foundationYearEnd", foundationYearEnd)).thenReturn(query);

        Mockito.when(query.getResultList()).thenThrow(NoResultException.class);
        Assertions.assertThrows(NoResultException.class, () -> {
            universityRepository.findAllFoundedUniversitiesBetweenYears(foundationYearStart, foundationYearEnd);
        });
    }
}
