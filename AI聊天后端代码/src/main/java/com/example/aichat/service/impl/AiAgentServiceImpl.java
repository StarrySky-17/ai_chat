package com.example.aichat.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aichat.entity.AiAgent;
import com.example.aichat.mapper.AiAgentMapper;
import com.example.aichat.service.AiAgentService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiAgentServiceImpl extends ServiceImpl<AiAgentMapper, AiAgent> implements AiAgentService {

    @Autowired
    private AiAgentMapper aiAgentMapper;

    @Override
    public ResultVo getAvailableAgents() {
        List<AiAgent> agents = aiAgentMapper.selectAvailableAgents();
        return ResultVo.success(agents);
    }

    @Override
    public ResultVo getAgentById(Long id) {
        AiAgent agent = aiAgentMapper.selectById(id);
        if (agent == null || agent.getStatus() != 1) {
            return ResultVo.error("智能体不存在或已禁用");
        }
        return ResultVo.success(agent);
    }

    @Override
    public ResultVo getAgentByName(String name) {
        AiAgent agent = aiAgentMapper.selectByName(name);
        if (agent == null) {
            return ResultVo.error("智能体不存在");
        }
        return ResultVo.success(agent);
    }

    @Override
    public ResultVo createAgent(AiAgent aiAgent) {
        // 检查智能体名称是否已存在
        AiAgent existingAgent = aiAgentMapper.selectByName(aiAgent.getName());
        if (existingAgent != null) {
            return ResultVo.error("智能体名称已存在");
        }

        aiAgent.setCreateTime(LocalDateTime.now());
        aiAgent.setUpdateTime(LocalDateTime.now());
        aiAgent.setStatus(1); // 默认启用

        int rows = aiAgentMapper.insert(aiAgent);
        if (rows > 0) {
            return ResultVo.success(aiAgent);
        } else {
            return ResultVo.error("创建智能体失败");
        }
    }

    @Override
    public ResultVo updateAgent(AiAgent aiAgent) {
        AiAgent existingAgent = aiAgentMapper.selectById(aiAgent.getId());
        if (existingAgent == null) {
            return ResultVo.error("智能体不存在");
        }

        // 检查名称是否已被其他智能体使用
        if (!existingAgent.getName().equals(aiAgent.getName())) {
            AiAgent nameAgent = aiAgentMapper.selectByName(aiAgent.getName());
            if (nameAgent != null) {
                return ResultVo.error("智能体名称已存在");
            }
        }

        aiAgent.setUpdateTime(LocalDateTime.now());
        int rows = aiAgentMapper.updateById(aiAgent);
        if (rows > 0) {
            return ResultVo.success("更新成功");
        } else {
            return ResultVo.error("更新失败");
        }
    }

    @Override
    public ResultVo deleteAgent(Long id) {
        AiAgent agent = aiAgentMapper.selectById(id);
        if (agent == null) {
            return ResultVo.error("智能体不存在");
        }

        // 逻辑删除
        agent.setStatus(0);
        agent.setUpdateTime(LocalDateTime.now());
        int rows = aiAgentMapper.updateById(agent);
        if (rows > 0) {
            return ResultVo.success("删除成功");
        } else {
            return ResultVo.error("删除失败");
        }
    }
}