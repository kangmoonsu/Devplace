package com.michael.devplace.dto;

import com.michael.devplace.entity.TechEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechDTO {

    private Integer id;

    private Integer studyId;

    private String tech;

    public static TechDTO toTechDTO(TechEntity techEntity){
        TechDTO.TechDTOBuilder techDTOBuilder = TechDTO.builder()
                .id(techEntity.getId())
                .studyId(techEntity.getStudyEntity().getId())
                .tech(techEntity.getTech());
        return techDTOBuilder.build();
    }
}
