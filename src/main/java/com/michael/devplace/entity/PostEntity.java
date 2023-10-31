package com.michael.devplace.entity;

import com.michael.devplace.dto.PostDTO;
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
@Table(name = "post")
public class PostEntity extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // user_id와 매핑
    private UserEntity userEntity;

    @Column
    private String title;

    @Column(length = 2000)
    private String content;

    @Column
    private String postType;

    @Column
    private String topic;

    @Column
    private int viewCnt;

    @Column
    private int fileExist; // true : 1 , false : 0

    @OneToMany(mappedBy = "postEntity" , cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity" , cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageEntity> imageEntityList = new ArrayList<>();

    public static PostEntity toSaveEntity(PostDTO postDTO, UserEntity userEntity){
        return PostEntity.builder()
                .id(postDTO.getId())
                .userEntity(userEntity)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .topic(postDTO.getTopic())
                .viewCnt(0)
                .fileExist(0) // 파일 없음
                .build();
    }

    public static PostEntity toSaveImageEntity(PostDTO postDTO, UserEntity userEntity){
        return PostEntity.builder()
                .id(postDTO.getId())
                .userEntity(userEntity)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .topic(postDTO.getTopic())
                .viewCnt(0)
                .fileExist(1) // 파일 존재
                .build();
    }

    public static PostEntity toUpdateEntity(PostDTO postDTO, UserEntity userEntity){
        return PostEntity.builder()
                .id(postDTO.getId())
                .userEntity(userEntity)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .viewCnt(postDTO.getViewCnt())
                .build();
    }
}
