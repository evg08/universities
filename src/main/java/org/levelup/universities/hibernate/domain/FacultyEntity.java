package org.levelup.universities.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "faculty")
public class FacultyEntity {
    @Id
    @Column(name="faculty_id")
    private Integer facultyId;
    private String name;
    @ManyToOne
    @JoinColumn(name="university_id")
    private UniversityEntity university;
    @ManyToMany
    @JoinTable(name = "faculty_subject",
    joinColumns =@JoinColumn(name = "faculty_id") ,
    inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Collection<SubjectEntity> subjects;

    @OneToMany(fetch = FetchType.EAGER,mappedBy ="faculty" )
    private List<DepartmentEntity> departments;
    //joinColumns -это facultyId

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacultyEntity that = (FacultyEntity) o;
        return facultyId.equals(that.facultyId) &&
                name.equals(that.name) &&
                university.equals(that.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, name, university);
    }
}

