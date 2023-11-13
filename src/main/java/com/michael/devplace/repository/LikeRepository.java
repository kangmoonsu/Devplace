package com.michael.devplace.repository;

import com.michael.devplace.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    LikeEntity findByUserEntityIdAndPostEntityId(Integer userId, Integer postId);

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.postEntity.id = :postId AND l.likeStatus = true")
    int getLikeCountByPostId(@Param("postId") Integer postId);

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.postEntity.id = :postId AND l.likeStatus = false")
    int getDislikeCountByPostId(@Param("postId") Integer postId);
}

