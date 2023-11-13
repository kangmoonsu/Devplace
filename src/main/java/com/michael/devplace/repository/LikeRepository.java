package com.michael.devplace.repository;

import com.michael.devplace.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {

    // 특정 사용자가 특정 게시물에 대한 좋아요/싫어요 여부 확인
    LikeEntity findByPostEntityIdAndUserEntityId(Integer postId, Integer userId);

    @Query(value = "SELECT SUM(liked) FROM like_entity WHERE post_id = :postId", nativeQuery = true)
    Integer sumLikesByPostId(@Param("postId")Integer postId);
}
