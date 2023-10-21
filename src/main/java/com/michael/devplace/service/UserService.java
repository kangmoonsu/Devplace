package com.michael.devplace.service;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void join(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userEntity.setMod_date(LocalDateTime.now());
        userEntity.setReg_date(LocalDateTime.now());
        userRepository.save(userEntity);
    }
}
