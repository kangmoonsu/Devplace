package com.michael.devplace.dto;

import com.michael.devplace.entity.ImageEntity;
import com.michael.devplace.entity.PostEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private List<String> originalFileName; // 원본 파일 이름

    private List<String> storedFileName;

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

        // 이미지 테이블에 외래 키로 연결된 경우 이미지가 있음을 가정
        if (postEntity.getImageEntityList() != null && !postEntity.getImageEntityList().isEmpty()) {
            postDTOBuilder
                    .originalFileName(buildOriginalFileNames(postEntity))
                    .storedFileName(buildStoredFileNames(postEntity));
        }
        return postDTOBuilder.build();
    }

    private static List<String> buildStoredFileNames(PostEntity postEntity) {
        List<String> storedFileNames = new ArrayList<>();
        for (ImageEntity image : postEntity.getImageEntityList()) {
            storedFileNames.add(image.getFilePath());
        }
        return storedFileNames;
    }

    private static List<String> buildOriginalFileNames(PostEntity postEntity) {
        List<String> originalFileNames = new ArrayList<>();
        for (ImageEntity image : postEntity.getImageEntityList()) {
            originalFileNames.add(image.getFileName());
        }
        return originalFileNames;
    }
}
