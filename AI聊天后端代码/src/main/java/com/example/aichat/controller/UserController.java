package com.example.aichat.controller;

import com.example.aichat.entity.User;
import com.example.aichat.service.UserService;
import com.example.aichat.vo.LoginVo;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginVo loginVo, HttpSession session) {
        ResultVo result = userService.login(loginVo);
        if (result.getCode() == 200) {
            session.setAttribute("userId", ((User) result.getData()).getId());
        }
        return result;
    }

    @PostMapping("/register")
    public ResultVo register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/info")
    public ResultVo getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error(401, "未登录");
        }
        return userService.getUserInfo(userId);
    }

    @PutMapping("/info")
    public ResultVo updateUserInfo(@RequestBody User user, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error(401, "未登录");
        }
        user.setId(userId);
        return userService.updateUserInfo(user);
    }

    @PostMapping("/logout")
    public ResultVo logout(HttpSession session) {
        session.invalidate();
        return ResultVo.success("退出成功");
    }
}