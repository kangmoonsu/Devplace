package com.michael.devplace.entity;

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

    @Column
    private Integer parent_id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;
}
