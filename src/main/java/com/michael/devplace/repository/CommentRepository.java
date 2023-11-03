package com.michael.devplace.repository;

import com.michael.devplace.entity.CommentEntity;
import com.michael.devplace.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findByPostEntityId(Integer id);

    List<CommentEntity> findAllByPostEntityOrderByIdDesc(PostEntity postEntity);
}
