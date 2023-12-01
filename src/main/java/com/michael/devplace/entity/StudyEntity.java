package com.michael.devplace.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study")
@SequenceGenerator(name = "article_seq", sequenceName = "seq_name", allocationSize = 1)
public class StudyEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // user_id와 매핑
    private UserEntity userEntity;

    @Column
    private String title;

    @Lob
    private String content;

    @Column
    private String studyType;

    @Column
    private String eventType;

    @Column
    private String eventPeriod;

    @Column
    private int capacity;

    @Column
    private String deadline;

    @Column
    private int viewCnt;

    @Column
    private String contact;

    @Column
    private String contactAddress;

    @OneToMany(mappedBy = "studyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TechEntity> techEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "studyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PositionEntity> positionEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "studyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
}
