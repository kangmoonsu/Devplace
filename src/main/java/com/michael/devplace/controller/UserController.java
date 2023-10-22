package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @PostMapping("/user/login")
    public String doLogin(UserDTO userDTO, HttpSession session){
        UserDTO user = userService.login(userDTO);
        if (user != null){
            session.setAttribute("user", user);
            return "redirect:/devplace/main";
        } else {
            return "login";
        }
    }

    @GetMapping("/user/join")
    public String join() {
        return "join";
    }

    @PostMapping("/user/join")
    public String doJoin(UserDTO userDTO){
        System.out.println(userDTO);
        userService.join(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "main";
    }
}
