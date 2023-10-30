package com.michael.devplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    @GetMapping("/community")
    public String communityPost(){
        return "communityPost";
    }
}
