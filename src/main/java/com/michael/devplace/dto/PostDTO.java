package com.michael.devplace.dto;

import com.michael.devplace.entity.PostEntity;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Integer id;

    private Integer userId;

    private String title;

    @Lob
    private String content;

    private String postType;

    private String topic;

    private int viewCnt;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime;

    public static PostDTO toPostDTO(PostEntity postEntity) {
        PostDTO.PostDTOBuilder postDTOBuilder = PostDTO.builder()
                .id(postEntity.getId())
                .userId(postEntity.getUserEntity().getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postType(postEntity.getPostType())
                .topic(postEntity.getTopic())
                .viewCnt(postEntity.getViewCnt())
                .updatedTime(postEntity.getUpdatedTime())
                .createdTime(postEntity.getCreatedTime());
        return postDTOBuilder.build();
    }
}
