package com.michael.devplace.dto;

import com.michael.devplace.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String comment;
    private LocalDateTime createdTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity){
        return CommentDTO.builder()
                .id(commentEntity.getId())
                .postId(commentEntity.getPostEntity().getId())
                .comment(commentEntity.getComment())
                .createdTime(commentEntity.getCreatedTime())
                .build();
    }
}
