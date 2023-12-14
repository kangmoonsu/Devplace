package com.michael.devplace.service;

import com.michael.devplace.dto.*;
import com.michael.devplace.entity.*;
import com.michael.devplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private TechRepository techRepository;
    @Autowired
    private PositionRepository positionRepository;

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

    // 댓글 목록
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

    public List<Map<String, Object>> getLatestFourQaPosts() {
        List<PostEntity> list = postRepository.findTop4ByPostTypeOrderByIdDesc("qa");
        return mainList(list);
    }

    public List<Map<String, Object>> getLatestFourCommunityPosts() {
        List<PostEntity> list = postRepository.findTop4ByPostTypeOrderByIdDesc("community");
        return mainList(list);
    }

    private List<Map<String, Object>> mainList(List<PostEntity> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (PostEntity postEntity : list) {
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);
            UserDTO userDTO = UserDTO.toUserDTO(postEntity.getUserEntity());
            int commentCnt = postEntity.getCommentCount();
            int postId = postEntity.getId();
            int likeCount = likeRepository.getLikeCountByPostId(postId);
            int dislikeCount = likeRepository.getDislikeCountByPostId(postId);
            int netLikes = likeCount - dislikeCount;

            Map<String, Object> postMap = new HashMap<>();
            postMap.put("netLikes", netLikes);
            postMap.put("postDTO", postDTO);
            postMap.put("userDTO", userDTO);
            postMap.put("commentCnt", commentCnt);
            resultList.add(postMap);
        }
        return resultList;
    }

    public void postRecruit(StudyDTO studyDTO, HttpSession session) {
        studyDTO.setPostType("recruit");
        UserEntity userEntity = UserEntity.toUpdateUserEntity((UserDTO) session.getAttribute("user"));
        PostEntity postEntity = PostEntity.toPostEntity(studyDTO, userEntity);

        // PostEntity 저장
        postRepository.save(postEntity);

        // StudyEntity 생성 및 저장
        StudyEntity studyEntity = StudyEntity.toStudyEntity(studyDTO);
        studyEntity.setPostEntity(postEntity);
        studyRepository.save(studyEntity);

        // TechEntity 리스트 생성 및 저장
        List<TechEntity> techEntityList = studyDTO.getTechStack().stream()
                .map(tech -> {
                    TechEntity techEntity = TechEntity.builder()
                            .tech(tech)
                            .studyEntity(studyEntity)
                            .build();
                    techEntity.setStudyEntity(studyEntity);
                    return techEntity;
                })
                .collect(Collectors.toList());
        techRepository.saveAll(techEntityList);

        // PositionEntity 리스트 생성 및 저장
        List<PositionEntity> positionEntityList = studyDTO.getPositions().stream()
                .map(position -> {
                    PositionEntity positionEntity = PositionEntity.builder()
                            .position(position)
                            .studyEntity(studyEntity)
                            .build();
                    positionEntity.setStudyEntity(studyEntity);
                    return positionEntity;
                })
                .collect(Collectors.toList());
        positionRepository.saveAll(positionEntityList);
    }
}
