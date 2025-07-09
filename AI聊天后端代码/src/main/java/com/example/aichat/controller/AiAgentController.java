package com.example.aichat.controller;

import com.example.aichat.entity.AiAgent;
import com.example.aichat.service.AiAgentService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/ai-agent")
public class AiAgentController {

    @Autowired
    private AiAgentService aiAgentService;

    @GetMapping
    public ResultVo getAvailableAgents() {
        return aiAgentService.getAvailableAgents();
    }

    @GetMapping("/{id}")
    public ResultVo getAgentById(@PathVariable Long id) {
        return aiAgentService.getAgentById(id);
    }

    @GetMapping("/name/{name}")
    public ResultVo getAgentByName(@PathVariable String name) {
        return aiAgentService.getAgentByName(name);
    }

    @PostMapping
    public ResultVo createAgent(@RequestBody AiAgent aiAgent, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        // 这里可以添加权限检查，例如只有管理员可以创建智能体
        return aiAgentService.createAgent(aiAgent);
    }

    @PutMapping
    public ResultVo updateAgent(@RequestBody AiAgent aiAgent, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        // 这里可以添加权限检查
        return aiAgentService.updateAgent(aiAgent);
    }

    @DeleteMapping("/{id}")
    public ResultVo deleteAgent(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        // 这里可以添加权限检查
        return aiAgentService.deleteAgent(id);
    }
}