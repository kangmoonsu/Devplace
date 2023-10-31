package com.michael.devplace.service;

import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.ImageEntity;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.ImageRepository;
import com.michael.devplace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public void postCommunity(PostDTO postDTO, HttpSession session) throws IOException {
        // 게시글 타입 설정
        postDTO.setPostType("community");
        // 현재 세션에서 사용자 정보 가져오기
        UserEntity userEntity = UserEntity.toUpdateUserEntity((UserDTO) session.getAttribute("user"));

        // 이미지가 있는지 확인
        if (postDTO.getImages().isEmpty()) {
            PostEntity.toSaveEntity(postDTO, userEntity);
        } else {
            // 게시글 엔티티 생성
            PostEntity postEntity = PostEntity.toSaveImageEntity(postDTO, userEntity);
            // 게시글 정보만 저장 (이미지는 나중에 처리)
            Integer savedId = postRepository.save(postEntity).getId();
            PostEntity post = postRepository.findById(savedId).get();
            for (MultipartFile postImg : postDTO.getImages()) {
                String originalImgName = postImg.getOriginalFilename();
                String storedImgName = System.currentTimeMillis() + "_" + originalImgName;
                String savePath = "C:/springboot_img/" + storedImgName;
                postImg.transferTo(new File(savePath));

                // 이미지 엔티티 생성 및 저장
                ImageEntity imageEntity = ImageEntity.toImageEntity(post, originalImgName, storedImgName);
                imageRepository.save(imageEntity);
            }
        }
    }
}

