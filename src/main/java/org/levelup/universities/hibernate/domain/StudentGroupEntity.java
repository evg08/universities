package org.levelup.universities.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student_group")
public class StudentGroupEntity {
    @Id
    @Column(name = "group_key",columnDefinition = "varchar(50)", unique = true)
    private String groupKey;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @OneToMany(fetch = FetchType.EAGER,mappedBy ="studentGroup",cascade ={CascadeType.PERSIST, CascadeType.REMOVE} )
    private List<StudentEntity> students;

    public StudentGroupEntity(String groupKey, DepartmentEntity department) {
        this.groupKey = groupKey;
        this.department = department;
    }
}
