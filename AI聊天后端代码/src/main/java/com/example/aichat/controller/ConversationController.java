package com.example.aichat.controller;

import com.example.aichat.service.ConversationService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public ResultVo createConversation(@RequestParam String title, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.createConversation(userId, title);
    }

    @GetMapping
    public ResultVo getConversations(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.getConversationsByUserId(userId, page, size);
    }

    @GetMapping("/{id}")
    public ResultVo getConversationDetail(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.getConversationById(id, userId);
    }

    @PutMapping("/{id}/title")
    public ResultVo updateConversationTitle(@PathVariable Long id,
                                           @RequestParam String title,
                                           HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.updateConversationTitle(id, userId, title);
    }

    @DeleteMapping("/{id}")
    public ResultVo deleteConversation(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.deleteConversation(id, userId);
    }

    @GetMapping("/recent")
    public ResultVo getRecentConversations(@RequestParam(defaultValue = "5") Integer limit, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return conversationService.getRecentConversations(userId, limit);
    }
}