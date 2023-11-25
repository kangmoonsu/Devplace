package com.michael.devplace.service;

import com.michael.devplace.dto.CommentDTO;
import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.CommentEntity;
import com.michael.devplace.entity.PostEntity;
import com.michael.devplace.entity.UserEntity;
import com.michael.devplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 커뮤니티 글 작성
    public void postCommunity(PostDTO postDTO, HttpSession session) {
        postDTO.setPostType("community");
        UserEntity userEntity = UserEntity.toUpdateUserEntity((UserDTO) session.getAttribute("user"));
         // 왜 updateEntity로 되어 있지?
        PostEntity postEntity = PostEntity.toSaveEntity(postDTO, userEntity);
        postRepository.save(postEntity);
    }
    // 커뮤니티 전체 버전
    @Transactional
    public Page<Map<String, Object>> communityList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByPostTypeOrderByIdDesc("community", pageable);
        return getPostDTOs(postEntities);
    }

    // 사는 얘기 전체 버전
    @Transactional
    public Page<Map<String, Object>> lifeList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByTopicOrderByIdDesc("life", pageable);
        return getPostDTOs(postEntities);
    }

    // 공유 전체 버전
    @Transactional
    public Page<Map<String, Object>> shareInfoList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByTopicOrderByIdDesc("shareInfo", pageable);
        return getPostDTOs(postEntities);
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
            int commentCnt = postEntity.getCommentCount();

            map.put("postDTO", postDTO);
            map.put("userDTO", userDTO);
            map.put("commentCnt", commentCnt);
        }
        return map;
    }

    public List<Map<String, Object>> commentList(Integer id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<CommentEntity> commentEntityList = commentRepository.findByPostEntityIdOrderByIdDesc(id);
        for (CommentEntity commentEntity : commentEntityList) {
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

    // 커뮤니티 전체 검색 버전
    public Page<Map<String, Object>> searchedCommunityList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchCommunityPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    // 사는얘기 검색 버전
    public Page<Map<String, Object>> searchedLifeList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchLifePosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    // 공유 검색 버전
    public Page<Map<String, Object>> searchedShareInfoList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchShareInfoPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    private Page<Map<String, Object>> getPostDTOs(Page<PostEntity> postEntities) {
        return postEntities.map(postEntity -> {
            Map<String, Object> map = new HashMap<>();

            // 게시물과 사용자 정보를 맵에 추가
            UserEntity userEntity = postEntity.getUserEntity();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);

            int commentCnt = postEntity.getCommentCount();

            // 추천 수
            int postId = postEntity.getId();
            int likeCount = likeRepository.getLikeCountByPostId(postId);
            int dislikeCount = likeRepository.getDislikeCountByPostId(postId);
            int netLikes = likeCount - dislikeCount;

            map.put("netLikes", netLikes);
            map.put("postDTO", postDTO);
            map.put("userDTO", userDTO);
            map.put("commentCnt", commentCnt);
            return map;
        });
    }

    public void postQa(PostDTO postDTO, HttpSession session) {
        postDTO.setPostType("qa");
        UserEntity userEntity = UserEntity.toUpdateUserEntity((UserDTO) session.getAttribute("user"));
        PostEntity postEntity = PostEntity.toSaveEntity(postDTO, userEntity);
        postRepository.save(postEntity);
    }

    // Qa 검색 리스트
    public Page<Map<String, Object>> searchedQaList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchedQaPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    // Qa 전체 리스트
    public Page<Map<String, Object>> qaList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByPostTypeOrderByIdDesc("qa", pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> searchedCareerList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchCareerPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> careerList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByTopicOrderByIdDesc("career", pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> searchedTechList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchTechPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> techList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByTopicOrderByIdDesc("tech", pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> searchedEtcList(String search, Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.searchEtcPosts(search, pageable);
        return getPostDTOs(postEntities);
    }

    public Page<Map<String, Object>> etcList(Pageable pageable) {
        int pageLimit = 10;
        int page = pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostEntity> postEntities = postRepository.findByTopicOrderByIdDesc("etc", pageable);
        return getPostDTOs(postEntities);
    }
}
