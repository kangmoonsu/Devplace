package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class RestfulController {

    @GetMapping("/checkSession")
    public Map<String, Object> checkSession(HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (userDTO == null){
            map.put("result","fail");
        } else {
            map.put("result", "success");
            map.put("id", userDTO.getId());
        }
        return map;
    }
}
