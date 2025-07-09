package com.example.aichat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aichat.entity.User;
import com.example.aichat.vo.LoginVo;
import com.example.aichat.vo.ResultVo;

public interface UserService extends IService<User> {

    ResultVo login(LoginVo loginVo);

    ResultVo register(User user);

    ResultVo getUserInfo(Long userId);

    ResultVo updateUserInfo(User user);

}