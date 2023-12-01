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
public class StudyEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String deadline;

    @OneToMany(mappedBy = "userEntity" , cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) // PostEntity 엔티티의 user 필드와 매핑
    private List<TechEntity> postEntityList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "positions", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "position")
    private List<String> positions;

    @Column
    private String contact;

    @Column
    private String contactAddress;
}
