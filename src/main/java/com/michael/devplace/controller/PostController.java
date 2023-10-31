package com.michael.devplace.controller;

import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/community")
    public String communityPost(){
        return "communityPost";
    }

    @PostMapping("/community")
    public String postCommunity(@ModelAttribute PostDTO postDTO, HttpSession session, List<MultipartFile> images){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        postService.postCommunity(userDTO, images, postDTO);

        return "redirect:/post/community";
    }
}
