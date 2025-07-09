package com.example.aichat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aichat.entity.Conversation;
import com.example.aichat.vo.ResultVo;

public interface ConversationService extends IService<Conversation> {

    ResultVo createConversation(Long userId, String title);

    ResultVo getConversationsByUserId(Long userId, Integer page, Integer size);

    ResultVo getConversationById(Long id, Long userId);

    ResultVo updateConversationTitle(Long id, Long userId, String title);

    ResultVo deleteConversation(Long id, Long userId);

    ResultVo getRecentConversations(Long userId, Integer limit);

}