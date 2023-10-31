package com.michael.devplace.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private PostEntity postEntity;

    @Column
    private String fileName;

    @Column
    private String filePath;

    public static ImageEntity toImageEntity(PostEntity post, String originalImgName, String storedImgName) {
        return ImageEntity.builder()
                .fileName(originalImgName)
                .filePath(storedImgName)
                .postEntity(post)
                .build();
    }
}
