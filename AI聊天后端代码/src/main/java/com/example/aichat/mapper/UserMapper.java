package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aichat.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(String username);

}