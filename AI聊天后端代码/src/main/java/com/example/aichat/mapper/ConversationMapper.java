package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aichat.entity.Conversation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConversationMapper extends BaseMapper<Conversation> {

    IPage<Conversation> selectByUserId(Page<Conversation> page, @Param("userId") Long userId);

    List<Conversation> selectRecentConversations(@Param("userId") Long userId, @Param("limit") Integer limit);

}