package com.michael.devplace.service;

import com.michael.devplace.dto.CommentDTO;
import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.CommentEntity;
import com.michael.devplace.entity.ImageEntity;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.CommentRepository;
import com.michael.devplace.repository.ImageRepository;
import com.michael.devplace.repository.PostRepository;
import com.michael.devplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void postCommunity(PostDTO postDTO, HttpSession session, MultipartFile[] imageArray) throws IOException {
        // 게시글 타입 설정
        postDTO.setPostType("community");
        // 현재 세션에서 사용자 정보 가져오기
        UserEntity userEntity = UserEntity.toUpdateUserEntity((UserDTO) session.getAttribute("user"));

        PostEntity postEntity = PostEntity.toSaveEntity(postDTO, userEntity);
        if (imageArray == null) {
            postRepository.save(postEntity);
        } else {
            Integer savedId = postRepository.save(postEntity).getId();
            PostEntity post = postRepository.findById(savedId).get();
            for (MultipartFile file : imageArray) {
                if (file.isEmpty()) {

                    continue;
                }
                String originalImgName = file.getOriginalFilename();
                String storedImgName = System.currentTimeMillis() + "_" + originalImgName;
                String savePath = "C:/springboot_img/" + storedImgName;
                file.transferTo(new File(savePath));

                ImageEntity imageEntity = ImageEntity.toImageEntity(post, originalImgName, storedImgName);
                imageRepository.save(imageEntity);
            }
        }
    }


    @Transactional
    public List<Map<String, Object>> communityList() {

        List<PostEntity> postEntityList = postRepository.findByPostTypeOrderByIdDesc("community");

        List<Map<String, Object>> list = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            Map<String, Object> map = new HashMap<>();

            UserEntity userEntity = postEntity.getUserEntity();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);

            map.put("postDTO", postDTO);
            map.put("userDTO", userDTO);

            list.add(map);
        }

        return list;
    }

    @Transactional
    public void addViewCount(Integer id) {
        postRepository.addViewCount(id);
    }

    @Transactional
    public Map<String, Object> findById(Integer id) {
        Map<String, Object> map = new HashMap<>();

        Optional<PostEntity> post = postRepository.findById(id);

        if (post.isPresent()) {
            PostEntity postEntity = post.get();
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);

            UserEntity userEntity = postEntity.getUserEntity();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);

            map.put("postDTO", postDTO);
            map.put("userDTO", userDTO);
        }

        return map;
    }

    public List<Map<String, Object>> commentList(Integer id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<CommentEntity> commentEntityList = commentRepository.findByPostEntityId(id);
        for (CommentEntity commentEntity : commentEntityList){
            Map<String, Object> map = new HashMap<>();

            UserEntity userEntity = commentEntity.getUserEntity();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);

            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity);

            map.put("userDTO", userDTO);
            map.put("commentDTO", commentDTO);
            list.add(map);
        }
        return list;
    }
}
