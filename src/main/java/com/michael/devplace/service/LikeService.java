package com.michael.devplace.service;

import com.michael.devplace.entity.LikeEntity;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.LikeRepository;
import com.michael.devplace.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void vote(Integer postId, Integer userId, boolean like) throws ChangeSetPersister.NotFoundException {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        LikeEntity likeEntity = likeRepository.findByUserEntityIdAndPostEntityId(userId, postId);

        if (likeEntity == null) {
            likeEntity = LikeEntity.builder()
                    .userEntity(UserEntity.builder().id(userId).build())
                    .postEntity(postEntity)
                    .likeStatus(like)
                    .build();
            likeRepository.save(likeEntity);
        } else {
            if (likeEntity.isLikeStatus() != like) {
                likeEntity.setLikeStatus(like);
                likeRepository.save(likeEntity);
            } else {
                // 이미 같은 상태로 투표한 경우에는 취소 처리
                likeRepository.delete(likeEntity);
            }
        }
    }

    public int getLikeCount(Integer postId) {
        return likeRepository.getLikeCountByPostId(postId);
    }

    public int getDislikeCount(Integer postId) {
        return likeRepository.getDislikeCountByPostId(postId);
    }

    @Transactional
    public boolean getUserLikeStatus(Integer postId, Integer userId) {
        LikeEntity likeEntity = likeRepository.findByUserEntityIdAndPostEntityId(userId, postId);
        return likeEntity != null && likeEntity.isLikeStatus();
    }
}
