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
public class PostEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // user_id와 매핑
    private UserEntity userEntity;

    @Column
    private String title;

    @Lob
    private String content;

    @Column
    private String postType;

    @Column
    private String topic;

    @Column
    private int viewCnt;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> postUserLikes = new ArrayList<>();

    // 메서드를 통해 동적으로 추천 수 및 비추천 수를 계산
    public int getLikeCnt() {
        return (int) postUserLikes.stream().filter(LikeEntity::isLikeStatus).count();
    }
    public int getDislikeCnt() {
        return (int) postUserLikes.stream().filter(like -> !like.isLikeStatus()).count();
    }

    // 게시물의 댓글 갯수
    public int getCommentCount() {
        return commentEntityList.size();
    }

    public static PostEntity toSaveEntity(PostDTO postDTO, UserEntity userEntity) {
        return PostEntity.builder()
                .id(postDTO.getId())
                .userEntity(userEntity)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .topic(postDTO.getTopic())
                .viewCnt(0)
                .build();
    }

    public static PostEntity toUpdateEntity(PostDTO postDTO, UserEntity userEntity) {
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
