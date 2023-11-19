package com.michael.devplace.controller;

import com.michael.devplace.dto.*;
import com.michael.devplace.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    // 커뮤니티 전체 페이지
    @GetMapping("/community")
    public String communityPost(){

        return "communityPost";
    }
    // 사는얘기
    @GetMapping("/community/life")
    public String lifePost(){
        return "life";
    }
    // 공유
    @GetMapping("/community/shareInfo")
    public String shareInfoPost(){
        return "shareInfo";
    }

    // 커뮤니티 글 작성
    @PostMapping("/community")
    public String postCommunity(PostDTO postDTO, HttpSession session) throws IOException {

        postService.postCommunity(postDTO, session);

        return "redirect:/post/community";
    }

    @PostMapping("/images")
    @ResponseBody
    public List<String> uploadImages(@RequestParam("images") List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalImgName = file.getOriginalFilename();
                    String storedImgName = System.currentTimeMillis() + "_" + originalImgName;
                    String savePath = "C:/springboot_img/" + storedImgName;
                    file.transferTo(new File(savePath));

                    // 만약 DB에 이미지 경로를 저장해야 한다면 여기서 저장 로직을 추가합니다.
                    // 예를 들어 ImageEntity imageEntity = ImageEntity.toImageEntity(originalImgName, storedImgName);
                    // imageRepository.save(imageEntity);

                    String imageUrl = "/springboot_img/" + storedImgName; // 이미지의 URL 경로
                    imageUrls.add(imageUrl); // 이미지 URL을 리스트에 추가합니다.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageUrls;
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

        return "communityDetail";
    }
}
