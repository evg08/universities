package org.levelup.universities.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "department")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "name", nullable = false)
    private String name;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id", nullable = false)
    private FacultyEntity faculty;

    @OneToMany(fetch = FetchType.EAGER,mappedBy ="department" ,cascade ={CascadeType.PERSIST, CascadeType.REMOVE}  )
    private List<StudentGroupEntity> studentGroups;

    public DepartmentEntity(String name, FacultyEntity faculty) {
        this.name = name;
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return id.equals(that.id) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
