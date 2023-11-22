package com.michael.devplace.repository;

import com.michael.devplace.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    // postType = community의 게시글 불러오는 List
    // 커뮤니티 전체 버전
    Page<PostEntity> findByPostTypeOrderByIdDesc(String postType, Pageable pageable);

    // 사는 얘기 / 공유 전체 버전
    Page<PostEntity> findByTopicOrderByIdDesc(String topic, Pageable pageable);

    // 조회수 1 증가
    @Modifying
    @Query("update PostEntity p set p.viewCnt = p.viewCnt + 1 where  p.id = :id")
    void addViewCount(@Param("id") Integer id);

    // 커뮤니티 전체 검색 버전
    @Query("SELECT p FROM PostEntity p " +
            "WHERE p.postType = 'community' " +
            "AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PostEntity> searchCommunityPosts(@Param("search")String search, Pageable pageable);

    // 사는 얘기 검색 버전
    @Query("SELECT p FROM PostEntity p " +
            "WHERE p.topic = 'life' " +
            "AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PostEntity> searchLifePosts(@Param("search")String search, Pageable pageable);

    // 공유 검색 버전
    @Query("SELECT p FROM PostEntity p " +
            "WHERE p.topic = 'shareInfo' " +
            "AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PostEntity> searchShareInfoPosts(@Param("search")String search, Pageable pageable);


    @Query("SELECT p FROM PostEntity p " +
            "WHERE p.postType = 'qa' " +
            "AND LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PostEntity> searchedQaPosts(String search, Pageable pageable);
}


