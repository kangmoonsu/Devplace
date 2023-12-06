package com.michael.devplace.entity;

import com.michael.devplace.dto.TechDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study_tech")
public class TechEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId")
    private StudyEntity studyEntity;

    private String tech;

    public static TechEntity toSaveEntity(TechDTO techDTO,StudyEntity studyEntity){
        return TechEntity.builder()
                .id(techDTO.getId())
                .studyEntity(studyEntity)
                .tech(techDTO.getTech())
                .build();
    }
}
