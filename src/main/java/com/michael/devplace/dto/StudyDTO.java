package com.michael.devplace.dto;

import com.michael.devplace.entity.PositionEntity;
import com.michael.devplace.entity.StudyEntity;
import com.michael.devplace.entity.TechEntity;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDTO {

    private Integer id;

    private Integer postId;

    private Integer userId;

    private String title;

    @Lob
    private String content;

    private String postType;

    private String topic;

    private int viewCnt;


    private String eventType;

    private String eventPeriod;

    private int capacity;

    private String deadline;

    private String contact;

    private String contactAddress;

    private List<String> positions;

    private List<String> techStack;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime;


    public static StudyDTO toStudyDTO(StudyEntity studyEntity){
        StudyDTO.StudyDTOBuilder studyDTOBuilder = StudyDTO.builder()
                .id(studyEntity.getId())
                .postId(studyEntity.getPostEntity().getId())
                .eventType(studyEntity.getEventType())
                .eventPeriod(studyEntity.getEventPeriod())
                .capacity(studyEntity.getCapacity())
                .deadline(studyEntity.getDeadline())
                .contact(studyEntity.getContact())
                .contactAddress(studyEntity.getContactAddress());
        return studyDTOBuilder.build();
    }
}
