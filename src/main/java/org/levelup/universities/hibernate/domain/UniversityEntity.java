package org.levelup.universities.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@ToString
@Table(name = "university")
public class UniversityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(name = "short_name",nullable = false,unique = true)
    private String shortName;
    @Column(name = "foundation_year",nullable = false)
    private Integer foundationYear;
   // @JoinColumn(name = "university_id")
    @OneToMany(mappedBy = "university",fetch = FetchType.LAZY)// EAGER название поля в дочерней таблицы FacultyEntity UniversityEntity university;
    private List<FacultyEntity> faculties;

    public UniversityEntity(String name,String shortName,Integer foundationYear){
        this.name=name;
        this.shortName=shortName;
        this.foundationYear=foundationYear;
        this.faculties=new ArrayList<>();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityEntity that = (UniversityEntity) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                shortName.equals(that.shortName) &&
                Objects.equals(foundationYear, that.foundationYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, foundationYear);
    }
}
