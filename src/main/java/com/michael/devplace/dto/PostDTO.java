package com.michael.devplace.dto;

import com.michael.devplace.entity.PostEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private String postType;

    private String topic;

    private int viewCnt;

    private List<MultipartFile> images;

    private String originalFileName; // 원본 파일 이름

    private String storedFileName;

    private boolean fileExist;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime;

    public static PostDTO toPostDTO(PostEntity postEntity){
        return PostDTO.builder()
                .id(postEntity.getId())
                .userId(postEntity.getUserEntity().getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .postType(postEntity.getPostType())
                .topic(postEntity.getTopic())
                .viewCnt(postEntity.getViewCnt())

                .updatedTime(postEntity.getUpdatedTime())
                .createdTime(postEntity.getCreatedTime())
                .build();

    }
}
