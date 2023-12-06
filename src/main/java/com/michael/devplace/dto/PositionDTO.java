package com.michael.devplace.dto;

import com.michael.devplace.entity.PositionEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDTO {

    private Integer id;

    private Integer articleId;

    private String position;

    public static PositionDTO toPositionDTO(PositionEntity positionEntity){
        PositionDTO.PositionDTOBuilder positionDTOBuilder = PositionDTO.builder()
                .id(positionEntity.getId())
                .articleId(positionEntity.getStudyEntity().getId())
                .position(positionEntity.getPosition());
        return positionDTOBuilder.build();
    }
}
