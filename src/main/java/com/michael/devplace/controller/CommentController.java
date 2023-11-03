package com.michael.devplace.controller;

import com.michael.devplace.dto.CommentDTO;
import com.michael.devplace.entity.CommentEntity;
import com.michael.devplace.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(CommentDTO commentDTO){
        Integer saveResult = commentService.save(commentDTO);
        if (saveResult != null){
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getPostId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

}
