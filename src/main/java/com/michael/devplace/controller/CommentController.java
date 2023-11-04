package com.michael.devplace.controller;

import com.michael.devplace.dto.CommentDTO;
import com.michael.devplace.service.CommentService;
import com.michael.devplace.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @PostMapping("/save")
    public ResponseEntity save(CommentDTO commentDTO) {
        Integer saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Map<String, Object>>> fetchComments(@RequestParam Integer postId) {
        System.out.println(postId);
        List<Map<String,Object>> commentList = postService.commentList(postId);

        return new ResponseEntity<List<Map<String,Object>>>(commentList, HttpStatus.OK);
    }
}
