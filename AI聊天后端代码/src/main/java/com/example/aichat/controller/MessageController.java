package com.example.aichat.controller;

import com.example.aichat.service.MessageService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResultVo sendMessage(@RequestParam Long conversationId,
                               @RequestParam String content,
                               @RequestParam Integer senderType,
                               HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return messageService.sendMessage(conversationId, userId, content, senderType);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResultVo getMessagesByConversationId(@PathVariable Long conversationId,
                                               @RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "20") Integer size,
                                               HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return messageService.getMessagesByConversationId(conversationId, userId, page, size);
    }

    @DeleteMapping("/{id}")
    public ResultVo deleteMessage(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return messageService.deleteMessage(id, userId);
    }

    @DeleteMapping("/conversation/{conversationId}")
    public ResultVo deleteMessagesByConversationId(@PathVariable Long conversationId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return messageService.deleteMessagesByConversationId(conversationId, userId);
    }

    @PostMapping("/latest")
    public ResultVo getLatestMessagesByConversationIds(@RequestBody List<Long> conversationIds, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return messageService.getLatestMessagesByConversationIds(conversationIds);
    }
}