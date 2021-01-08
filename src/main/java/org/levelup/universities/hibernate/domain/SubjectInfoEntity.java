package org.levelup.universities.hibernate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="subject_info")
public class SubjectInfoEntity {
    @Id
    @Column(name="subject_id")
    private Integer subjectId;
    private String description;
    @Column(name = "room_number")
    private Integer roomNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn // replace @JoinColumn(name="subject_id")
    private SubjectEntity subject;

}
