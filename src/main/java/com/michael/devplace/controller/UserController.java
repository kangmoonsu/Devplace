package com.michael.devplace.controller;

import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login") // 로그인 페이지
    public String login() {
        return "login";
    }

    @PostMapping("/user/login") // 로그인 실행
    public String doLogin(UserDTO userDTO, HttpSession session) {
        UserDTO user = userService.login(userDTO);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/main";
        } else {
            return "login";
        }
    }

    @GetMapping("/user/join") // 회원가입 페이지
    public String join() {
        return "join";
    }

    @PostMapping("/user/join") // 회원가입 실행
    public String doJoin(UserDTO userDTO) throws IOException {
        System.out.println(userDTO);
        userService.join(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user/logout") // 로그아웃
    public String logout(HttpSession session) {
        session.invalidate();
        return "main";
    }

    @PostMapping("/user/email-check") // 이메일 중복 체크
    public @ResponseBody String emailCheck(@RequestParam("email") String email) {
        String checkResult = userService.emailCheck(email);
        if (checkResult != null){
            return "ok";
        } else {
            return "no";
        }
    }

    @PostMapping("/user/nickname-check") // 닉네임 중복 체크
    public @ResponseBody String nicknameCheck(@RequestParam("nickname") String nickname){
        String checkResult = userService.nicknameCheck(nickname);
        if (checkResult != null){
            return "ok";
        } else {
            return "no";
        }
    }
}
