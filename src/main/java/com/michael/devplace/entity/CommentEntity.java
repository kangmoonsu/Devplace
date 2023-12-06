package com.michael.devplace.entity;

import com.michael.devplace.dto.CommentDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity postEntity;

    @ManyToOne
    @JoinColumn(name = "userId") //
    private UserEntity userEntity;

    @Column(length = 1000)
    private String comment;

    @Column
    private Integer parentId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, PostEntity postEntity, UserEntity userEntity){
        return CommentEntity.builder()
                .postEntity(postEntity)
                .userEntity(userEntity)
                .comment(commentDTO.getComment())
                .createdTime(commentDTO.getCreatedTime())
                .build();
    }
}
