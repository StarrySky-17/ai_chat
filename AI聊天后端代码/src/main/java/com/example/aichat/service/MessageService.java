package com.example.aichat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aichat.entity.Message;
import com.example.aichat.vo.ResultVo;

import java.util.List;

public interface MessageService extends IService<Message> {

    ResultVo sendMessage(Long conversationId, Long userId, String content, Integer senderType);

    ResultVo getMessagesByConversationId(Long conversationId, Long userId, Integer page, Integer size);

    ResultVo deleteMessage(Long id, Long userId);

    ResultVo deleteMessagesByConversationId(Long conversationId, Long userId);

    ResultVo getLatestMessagesByConversationIds(List<Long> conversationIds);

}