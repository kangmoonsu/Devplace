package com.michael.devplace.entity;

import com.michael.devplace.dto.StudyDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study")
public class StudyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId") // user_id와 매핑
    private PostEntity postEntity;

    @Column
    private String eventType;

    @Column
    private String eventPeriod;

    @Column
    private int capacity;

    @Column
    private String deadline;

    @Column
    private String contact;

    @Column
    private String contactAddress;

    @OneToMany(mappedBy = "studyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TechEntity> techEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "studyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PositionEntity> positionEntityList = new ArrayList<>();


    public static StudyEntity toStudyEntity(StudyDTO studyDTO) {
        StudyEntity.StudyEntityBuilder studyEntityBuilder = StudyEntity.builder()
                .eventType(studyDTO.getEventType())
                .eventPeriod(studyDTO.getEventPeriod())
                .capacity(studyDTO.getCapacity())
                .deadline(studyDTO.getDeadline())
                .contact(studyDTO.getContact())
                .contactAddress(studyDTO.getContactAddress());

        // TechEntity 리스트로 변환 및 저장
        List<TechEntity> techEntityList = studyDTO.getTechStack().stream()
                .map(tech -> TechEntity.builder().tech(tech).studyEntity(studyEntityBuilder.build()).build())
                .collect(Collectors.toList());
        studyEntityBuilder.techEntityList(techEntityList);

        // PositionEntity 리스트로 변환 및 저장
        List<PositionEntity> positionEntityList = studyDTO.getPositions().stream()
                .map(position -> PositionEntity.builder().position(position).studyEntity(studyEntityBuilder.build()).build())
                .collect(Collectors.toList());
        studyEntityBuilder.positionEntityList(positionEntityList);

        return studyEntityBuilder.build();
    }

}

