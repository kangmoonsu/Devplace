package com.michael.devplace.service;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(UserDTO userDTO) throws IOException {
        if (userDTO.getImage() != null && !userDTO.getImage().isEmpty()) {
            MultipartFile file = userDTO.getImage();
            String originalFileName = file.getOriginalFilename();
            String saveFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "C:/springboot_img/" + saveFileName;
            file.transferTo(new File(savePath));
            userDTO.setImagePath(saveFileName);
        } else {
            userDTO.setImagePath(null); // 이미지를 선택하지 않았을 때, 이미지 경로를 null로 설정
        }

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

    public String emailCheck(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            return null;
        } else {
            return "ok";
        }
    }

    public String nicknameCheck(String nickname) {
        Optional<UserEntity> byNickname = userRepository.findByNickname(nickname);
        if (byNickname.isPresent()){
            return null;
        } else {
            return "ok";
        }
    }
}
