package com.michael.devplace.dto;

import com.michael.devplace.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    private String email;

    private String password;

    private String nickname;

    private String role;

    private String position;

    private LocalDateTime updatedTime;

    private LocalDateTime createdTime;

    public static UserDTO toUserDTO(UserEntity userEntity){
        return UserDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .role(userEntity.getRole())
                .position(userEntity.getPosition())
                .updatedTime(userEntity.getUpdatedTime())
                .createdTime(userEntity.getCreatedTime())
                .build();
    }
}
