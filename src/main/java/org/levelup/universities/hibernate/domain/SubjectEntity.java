package org.levelup.universities.hibernate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "subject")
public class SubjectEntity {
    @Id
    private Integer id;
    private String subject;
    @Column(name="count_of_hours")
    private Integer hours;
    @ManyToMany(mappedBy = "subjects",fetch = FetchType.EAGER)
    private Collection <FacultyEntity>faculties;

    @OneToOne(mappedBy = "subject",cascade ={CascadeType.PERSIST, CascadeType.REMOVE}) //field in subjectInfoentity  biderection
   // @JoinColumn(name="subject_id")  //one directional
    private SubjectInfoEntity info;

    public SubjectEntity(int id,String subject,int hours){
        this.id=id;
        this.subject=subject;
        this.hours=hours;
        this.faculties=new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return id.equals(that.id) &&
                subject.equals(that.subject) &&
                hours.equals(that.hours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, hours);
    }
}
