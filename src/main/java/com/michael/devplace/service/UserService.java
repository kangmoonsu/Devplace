package com.michael.devplace.service;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void join(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO userDTO) {
        /*
         1. 회원이 입력한 이메일로 DB 조회를 함
         2. DB 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        */
        Optional<UserEntity> byEmail = userRepository.findByEmail(userDTO.getEmail());

        if (byEmail.isPresent()) {
            UserEntity userEntity = byEmail.get();
            if (userEntity.getPassword().equals(userDTO.getPassword())) {
                return UserDTO.toUserDTO(userEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
