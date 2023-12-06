package com.michael.devplace.service;

import com.michael.devplace.dto.CommentDTO;
import com.michael.devplace.entity.CommentEntity;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.CommentRepository;
import com.michael.devplace.repository.PostRepository;
import com.michael.devplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Integer save(CommentDTO commentDTO) {
        Optional<PostEntity> byId = postRepository.findById(commentDTO.getPostId());
        Optional<UserEntity> byUser = userRepository.findById(commentDTO.getUserId());
        if (byId.isPresent() && byUser.isPresent()) {
            PostEntity postEntity = byId.get();
            UserEntity userEntity = byUser.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, postEntity, userEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }


    }
    public List<CommentDTO> findAll(Integer postId) {
        PostEntity postEntity = postRepository.findById(postId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByPostEntityOrderByIdDesc(postEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList){
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }
}
