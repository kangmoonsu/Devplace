package com.michael.devplace.repository;

import com.michael.devplace.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    // postType = community의 게시글 불러오는 List
    List<PostEntity> findByPostTypeOrderByIdDesc(String postType);

    // 조회수 1 증가
    @Modifying
    @Query("update PostEntity p set p.viewCnt = p.viewCnt + 1 where  p.id = :id")
    void addViewCount(@Param("id") Integer id);
}

