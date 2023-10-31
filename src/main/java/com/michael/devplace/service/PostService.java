package com.michael.devplace.service;

import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void postCommunity(UserDTO userDTO, List<MultipartFile> images, PostDTO postDTO) {
        postDTO.setUserId(userDTO.getId());

        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        PostEntity postEntity = PostEntity.toSaveEntity(postDTO, userEntity);

        postRepository.save(postEntity);
        System.out.println(postEntity);
    }
}
