package org.levelup.universities.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @Column(name = "student_card",unique =true )
    private Integer studentCard;

    @Column(name = "last_name",nullable = true)
    private String lastName;

    @Column(name = "first_name",nullable = true)
    private String firstName;

    @Column(name = "birthday",nullable = true)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "group_key", nullable = false)
    private StudentGroupEntity  studentGroup;

    public StudentEntity(Integer studentCard,String lastName, String firstName, Date birthday, StudentGroupEntity studentGroup) {

        this.studentCard=studentCard;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.studentGroup = studentGroup;
    }
}
