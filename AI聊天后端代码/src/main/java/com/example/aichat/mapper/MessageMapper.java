package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aichat.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {

    IPage<Message> selectMessagesByConversationId(Page<Message> page, @Param("conversationId") Long conversationId);

    List<Message> selectLatestMessagesByConversationIds(@Param("conversationIds") List<Long> conversationIds);

    int deleteMessagesByConversationId(@Param("conversationId") Long conversationId);

}