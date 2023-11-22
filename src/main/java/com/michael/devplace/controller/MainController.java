package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

    // 커뮤니티 전체 리스트
    @GetMapping("/main/community")
    public String community(@RequestParam(name = "search", required = false) String search, HttpSession session, @PageableDefault(page = 1) Pageable pageable, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }
        Page<Map<String, Object>> communityList;
        if (search != null && !search.isEmpty()) {
            // 검색어로 필터링된 포스트 목록 가져오기
            communityList = postService.searchedCommunityList(search, pageable);
            model.addAttribute("search", search);
        } else {
            // 검색어가 없는 경우 모든 포스트 가져오기
            communityList = postService.communityList(pageable);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), communityList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("communityList", communityList);
        return "community";
    }


    // 사는얘기
    @GetMapping("/main/community/life")
    public String lifePost(@RequestParam(name = "search", required = false) String search, HttpSession session, @PageableDefault(page = 1) Pageable pageable, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }

        Page<Map<String, Object>> communityList;
        System.out.println(search);
        if (search != null && !search.isEmpty()) {
            // 검색어로 필터링된 포스트 목록 가져오기
            communityList = postService.searchedLifeList(search, pageable);
            model.addAttribute("search", search);
        } else {
            // 검색어가 없는 경우 모든 포스트 가져오기
            communityList = postService.lifeList(pageable);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), communityList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("communityList", communityList);
        return "life";
    }
    // 공유
    @GetMapping("/main/community/shareInfo")
    public String shareInfo(@RequestParam(name = "search", required = false) String search, HttpSession session, @PageableDefault(page = 1) Pageable pageable, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }

        Page<Map<String, Object>> communityList;
        System.out.println(search);
        if (search != null && !search.isEmpty()) {
            // 검색어로 필터링된 포스트 목록 가져오기
            communityList = postService.searchedShareInfoList(search, pageable);
            model.addAttribute("search", search);
        } else {
            // 검색어가 없는 경우 모든 포스트 가져오기
            communityList = postService.shareInfoList(pageable);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), communityList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("communityList", communityList);
        return "shareInfo";
    }

    @GetMapping("/main/qa")
    public String qa(@RequestParam(name = "search", required = false) String search, HttpSession session, @PageableDefault(page = 1) Pageable pageable, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("userDTO", userDTO);
        }
        Page<Map<String, Object>> qaList;
        if (search != null && !search.isEmpty()) {
            // 검색어로 필터링된 포스트 목록 가져오기
            qaList = postService.searchedQaList(search, pageable);
            model.addAttribute("search", search);
        } else {
            // 검색어가 없는 경우 모든 포스트 가져오기
            qaList = postService.qaList(pageable);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), qaList.getTotalPages());

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("qaList", qaList);

        return "qa";
    }

    @GetMapping("/main/tech")
    public String tech(){
        return "tech";
    }

    @GetMapping("/main/career")
    public String career(){
        return "career";
    }
}
