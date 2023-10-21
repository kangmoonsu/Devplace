package com.michael.devplace.entity;

import com.michael.devplace.dto.UserDTO;
import lombok.Data;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String role;

    private String position;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "mod_date")
    private LocalDateTime mod_date;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime reg_date;

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setRole(userDTO.getRole());
        userEntity.setPosition(userDTO.getPosition());
        userEntity.setMod_date(userDTO.getMod_date());
        userEntity.setReg_date(userDTO.getReg_date());

        return userEntity;
    }

    public static UserEntity toModifyUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setRole(userDTO.getRole());
        userEntity.setPosition(userDTO.getPosition());
        userEntity.setMod_date(userDTO.getMod_date());
        userEntity.setReg_date(userDTO.getReg_date());

        return userEntity;
    }
}
