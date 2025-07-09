package com.example.aichat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aichat.entity.AiAgent;
import com.example.aichat.vo.ResultVo;

import java.util.List;

public interface AiAgentService extends IService<AiAgent> {

    ResultVo getAvailableAgents();

    ResultVo getAgentById(Long id);

    ResultVo getAgentByName(String name);

    ResultVo createAgent(AiAgent aiAgent);

    ResultVo updateAgent(AiAgent aiAgent);

    ResultVo deleteAgent(Long id);

}