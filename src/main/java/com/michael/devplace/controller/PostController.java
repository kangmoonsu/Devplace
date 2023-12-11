package com.michael.devplace.controller;

import com.michael.devplace.dto.*;
import com.michael.devplace.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/community")
    public String communityPost(){
        return "post/communityPost";
    }

    @GetMapping("/qa")
    public String qaPost(){
        return "post/qaPost";
    }

    @GetMapping("/study")
    public String studyPost(){
        return "post/studyPost";
    }

    // 커뮤니티 글 상세
    @GetMapping("/{id}")
    public String communityDetail(@PathVariable Integer id, Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null){
            model.addAttribute("userDTO", userDTO);
        }
        postService.addViewCount(id);// 클릭 당 조회수 1 증가
        Map<String, Object> detail = postService.findById(id);
        model.addAttribute("detail", detail);
        return "postDetail";
    }
}
