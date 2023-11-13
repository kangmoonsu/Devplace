package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class RestfulController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/checkSession")
    public Map<String, Object> checkSession(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (userDTO == null) {
            map.put("result", "fail");
        } else {
            map.put("result", "success");
            map.put("id", userDTO.getId());
        }
        return map;
    }

    // 좋아요 싫어요 처리하는 공간

}
