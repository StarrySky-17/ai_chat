package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aichat.entity.AiAgent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AiAgentMapper extends BaseMapper<AiAgent> {

    List<AiAgent> selectAvailableAgents();

    AiAgent selectByName(@Param("name") String name);

}