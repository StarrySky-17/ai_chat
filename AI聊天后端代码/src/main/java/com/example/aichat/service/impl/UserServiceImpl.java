package com.example.aichat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aichat.entity.User;
import com.example.aichat.mapper.UserMapper;
import com.example.aichat.service.UserService;
import com.example.aichat.vo.LoginVo;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo login(LoginVo loginVo) {
        // 查询用户
        User user = userMapper.selectByUsername(loginVo.getUsername());
        if (user == null) {
            return ResultVo.error("用户名不存在");
        }

        // 验证密码
        String md5Password = DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes());
        if (!md5Password.equals(user.getPassword())) {
            return ResultVo.error("密码错误");
        }

        // 验证状态
        if (user.getStatus() == 0) {
            return ResultVo.error("账号已禁用");
        }

        // 返回用户信息（排除密码）
        user.setPassword(null);
        return ResultVo.success(user);
    }

    @Override
    public ResultVo register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return ResultVo.error("用户名已存在");
        }

        // 设置默认值
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus(1); // 默认为启用状态

        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        // 保存用户
        int rows = userMapper.insert(user);
        if (rows > 0) {
            return ResultVo.success("注册成功");
        } else {
            return ResultVo.error("注册失败");
        }
    }

    @Override
    public ResultVo getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return ResultVo.error("用户不存在");
        }
        user.setPassword(null);
        return ResultVo.success(user);
    }

    @Override
    public ResultVo updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        int rows = userMapper.updateById(user);
        if (rows > 0) {
            return ResultVo.success("更新成功");
        } else {
            return ResultVo.error("更新失败");
        }
    }
}