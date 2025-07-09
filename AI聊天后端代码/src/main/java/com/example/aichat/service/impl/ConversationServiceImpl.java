package com.example.aichat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aichat.entity.Conversation;
import com.example.aichat.mapper.ConversationMapper;
import com.example.aichat.service.ConversationService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public ResultVo createConversation(Long userId, String title) {
        Conversation conversation = new Conversation();
        conversation.setUserId(userId);
        conversation.setTitle(title);
        conversation.setCreateTime(LocalDateTime.now());
        conversation.setUpdateTime(LocalDateTime.now());
        conversation.setStatus(1); // 正常状态

        int rows = conversationMapper.insert(conversation);
        if (rows > 0) {
            return ResultVo.success(conversation);
        } else {
            return ResultVo.error("创建对话失败");
        }
    }

    @Override
    public ResultVo getConversationsByUserId(Long userId, Integer page, Integer size) {
        Page<Conversation> pagination = new Page<>(page, size);
        IPage<Conversation> conversationPage = conversationMapper.selectByUserId(pagination, userId);
        return ResultVo.success(conversationPage);
    }

    @Override
    public ResultVo getConversationById(Long id, Long userId) {
        Conversation conversation = conversationMapper.selectById(id);
        if (conversation == null) {
            return ResultVo.error("对话不存在");
        }
        if (!conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权访问该对话");
        }
        return ResultVo.success(conversation);
    }

    @Override
    public ResultVo updateConversationTitle(Long id, Long userId, String title) {
        Conversation conversation = conversationMapper.selectById(id);
        if (conversation == null) {
            return ResultVo.error("对话不存在");
        }
        if (!conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权修改该对话");
        }

        conversation.setTitle(title);
        conversation.setUpdateTime(LocalDateTime.now());
        int rows = conversationMapper.updateById(conversation);
        if (rows > 0) {
            return ResultVo.success("更新成功");
        } else {
            return ResultVo.error("更新失败");
        }
    }

    @Override
    public ResultVo deleteConversation(Long id, Long userId) {
        Conversation conversation = conversationMapper.selectById(id);
        if (conversation == null) {
            return ResultVo.error("对话不存在");
        }
        if (!conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权删除该对话");
        }

        // 逻辑删除（实际项目可根据需求改为物理删除）
        conversation.setStatus(0);
        conversation.setUpdateTime(LocalDateTime.now());
        int rows = conversationMapper.updateById(conversation);
        if (rows > 0) {
            return ResultVo.success("删除成功");
        } else {
            return ResultVo.error("删除失败");
        }
    }

    @Override
    public ResultVo getRecentConversations(Long userId, Integer limit) {
        List<Conversation> conversations = conversationMapper.selectRecentConversations(userId, limit);
        return ResultVo.success(conversations);
    }
}