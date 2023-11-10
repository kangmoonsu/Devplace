package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private PostService postService;

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }
        return "main";
    }

    @GetMapping("/main/community")
    public String community(@RequestParam(name = "search", required = false) String search, HttpSession session, @PageableDefault(page = 1) Pageable pageable, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }

        Page<Map<String, Object>> communityList;
        System.out.println(search);
        if (search != null && !search.isEmpty()) {
            // 검색어로 필터링된 포스트 목록 가져오기
            communityList = postService.searchedCommunityList(search, pageable);
            model.addAttribute("search", search);
        } else {
            // 검색어가 없는 경우 모든 포스트 가져오기
            communityList = postService.communityList(pageable);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), communityList.getTotalPages());



        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("communityList", communityList);
        return "community";
    }
}
