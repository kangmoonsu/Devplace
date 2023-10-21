package com.michael.devplace.dto;

import com.michael.devplace.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String role;

    private String position;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "mod_date")
    private LocalDateTime mod_date;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime reg_date;

    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setNickname(userEntity.getNickname());
        userDTO.setRole(userEntity.getRole());
        userDTO.setPosition(userEntity.getPosition());
        userDTO.setMod_date(userEntity.getMod_date());
        userDTO.setReg_date(userEntity.getReg_date());

        return userDTO;
    }
}
