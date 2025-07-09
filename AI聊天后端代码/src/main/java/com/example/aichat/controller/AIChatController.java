package com.example.aichat.controller;

import com.example.aichat.service.AIChatService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/ai")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    @PostMapping("/chat")
    public ResultVo chatWithAI(@RequestParam String prompt, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return aiChatService.getAIResponse(prompt);
    }

    @PostMapping("/chat/stream")
    public SseEmitter streamChatWithAI(@RequestParam String prompt, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            SseEmitter emitter = new SseEmitter();
            try {
                emitter.send(SseEmitter.event().data(ResultVo.error("请先登录")));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
            return emitter;
        }
        ResultVo result = aiChatService.getStreamAIResponse(prompt);
        return (SseEmitter) result.getData();
    }
}