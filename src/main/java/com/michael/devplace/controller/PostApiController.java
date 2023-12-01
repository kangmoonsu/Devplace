package com.michael.devplace.controller;

import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostApiController {
    @Autowired
    private PostService postService;

    @PostMapping("/community")
    public ResponseEntity<Integer> communitySave(@RequestBody PostDTO postDTO, HttpSession session){
        postService.postCommunity(postDTO, session);
        return new ResponseEntity<>(HttpStatus.OK.value(), HttpStatus.OK);
    }
    @PostMapping("/qa")
    public ResponseEntity<Integer> qaSave(@RequestBody PostDTO postDTO, HttpSession session){
        postService.postQa(postDTO, session);
        return new ResponseEntity<>(HttpStatus.OK.value(), HttpStatus.OK);
    }
//
//    @PostMapping("/study")
//    public ResponseEntity<Integer> studySave(@){
//
//        return new ResponseEntity<>(HttpStatus.OK.value(), HttpStatus.OK);
//    }

}
