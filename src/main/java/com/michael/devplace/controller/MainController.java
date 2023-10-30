package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null){
            model.addAttribute("userDTO", userDTO);
        }
        return "main";
    }

    @GetMapping("/main/community")
    public String community(){
        return "community";
    }
}
