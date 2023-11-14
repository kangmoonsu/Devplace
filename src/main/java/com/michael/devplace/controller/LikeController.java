package com.michael.devplace.controller;

import com.michael.devplace.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/vote")
    public Map<String, Object> vote(@RequestParam Integer postId, @RequestParam Integer userId, @RequestParam boolean like) throws ChangeSetPersister.NotFoundException {
        likeService.vote(postId, userId, like);

        Map<String, Object> map = new HashMap<>();

        int likeCount = likeService.getLikeCount(postId) - likeService.getDislikeCount(postId);
        boolean userLikeStatus = likeService.getUserLikeStatus(postId, userId);

        map.put("likeCount", likeCount);
        map.put("userLikeStatus", userLikeStatus);

        return map;
    }

    @GetMapping("/getLikes")
    public Map<String, Object> getLikes(@RequestParam Integer postId) {
        Map<String, Object> map = new HashMap<>();
        int likeCount = likeService.getLikeCount(postId) - likeService.getDislikeCount(postId);
        map.put("likeCount", likeCount);
        return map;
    }

    @GetMapping("/userLikeStatus")
    public Map<String, Object> getUserVoteStatus(@RequestParam Integer postId, @RequestParam Integer userId) {
        Map<String, Object> map = new HashMap<>();
        boolean userLikeStatus = likeService.getUserLikeStatus(postId, userId);
        map.put("userLikeStatus", userLikeStatus);
        return map;
    }
}
