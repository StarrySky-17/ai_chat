package com.example.aichat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aichat.entity.Conversation;
import com.example.aichat.entity.Message;
import com.example.aichat.mapper.ConversationMapper;
import com.example.aichat.mapper.MessageMapper;
import com.example.aichat.service.MessageService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ConversationMapper conversationMapper;

    @Override
    public ResultVo sendMessage(Long conversationId, Long userId, String content, Integer senderType) {
        // 验证对话是否存在且属于当前用户
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null || !conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权访问该对话");
        }

        Message message = new Message();
        message.setConversationId(conversationId);
        message.setUserId(userId);
        message.setContent(content);
        message.setSenderType(senderType);
        message.setSendTime(LocalDateTime.now());
        message.setStatus(1); // 正常状态

        int rows = messageMapper.insert(message);
        if (rows > 0) {
            // 更新对话的最后更新时间
            conversation.setUpdateTime(LocalDateTime.now());
            conversationMapper.updateById(conversation);
            return ResultVo.success(message);
        } else {
            return ResultVo.error("发送消息失败");
        }
    }

    @Override
    public ResultVo getMessagesByConversationId(Long conversationId, Long userId, Integer page, Integer size) {
        // 验证对话是否存在且属于当前用户
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null || !conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权访问该对话");
        }

        Page<Message> pagination = new Page<>(page, size);
        IPage<Message> messagePage = messageMapper.selectMessagesByConversationId(pagination, conversationId);
        return ResultVo.success(messagePage);
    }

    @Override
    public ResultVo deleteMessage(Long id, Long userId) {
        Message message = messageMapper.selectById(id);
        if (message == null) {
            return ResultVo.error("消息不存在");
        }

        // 验证消息所属对话是否属于当前用户
        Conversation conversation = conversationMapper.selectById(message.getConversationId());
        if (conversation == null || !conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权删除该消息");
        }

        // 逻辑删除
        message.setStatus(0);
        int rows = messageMapper.updateById(message);
        if (rows > 0) {
            return ResultVo.success("删除成功");
        } else {
            return ResultVo.error("删除失败");
        }
    }

    @Override
    public ResultVo deleteMessagesByConversationId(Long conversationId, Long userId) {
        // 验证对话是否存在且属于当前用户
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null || !conversation.getUserId().equals(userId)) {
            return ResultVo.error("无权访问该对话");
        }

        int rows = messageMapper.deleteMessagesByConversationId(conversationId);
        return ResultVo.success("成功删除" + rows + "条消息");
    }

    @Override
    public ResultVo getLatestMessagesByConversationIds(List<Long> conversationIds) {
        List<Message> messages = messageMapper.selectLatestMessagesByConversationIds(conversationIds);
        return ResultVo.success(messages);
    }
}