package com.michael.devplace.controller;

import com.michael.devplace.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/vote")
    public Map<String, Object> vote(@RequestParam Integer postId, @RequestParam Integer userId, @RequestParam boolean like) throws ChangeSetPersister.NotFoundException {
        Map<String, Object> map = new HashMap<>();
        likeService.vote(postId, userId, like);
        int likeCount = likeService.getLikeCount(postId) - likeService.getDislikeCount(postId);
        map.put("likeCount", likeCount);
        return map;
    }
}
