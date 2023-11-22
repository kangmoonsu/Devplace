package com.michael.devplace.entity;

import com.michael.devplace.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String position;

    @Column
    private String imagePath;

    @OneToMany(mappedBy = "userEntity" , cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) // PostEntity 엔티티의 user 필드와 매핑
    private List<PostEntity> postEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity" , cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) // PostEntity 엔티티의 user 필드와 매핑
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static UserEntity toUserEntity(UserDTO userDTO) {

        if (userDTO.getImage() == null) {
            userDTO.setImagePath("");
        }

        return UserEntity.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .position(userDTO.getPosition())
                .imagePath(userDTO.getImagePath())
                .build();
    }

    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickname(userDTO.getNickname())
                .position(userDTO.getPosition())
                .imagePath(userDTO.getImagePath())
                .build();
    }
}
