package com.example.aichat.controller;

import com.example.aichat.service.QuickQuestionService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/quick-question")
public class QuickQuestionController {

    @Autowired
    private QuickQuestionService quickQuestionService;

    @PostMapping
    public ResultVo addQuickQuestion(@RequestParam String question, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return quickQuestionService.addQuickQuestion(userId, question);
    }

    @GetMapping
    public ResultVo getQuickQuestions(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return quickQuestionService.getQuickQuestionsByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResultVo updateQuickQuestion(@PathVariable Long id,
                                       @RequestParam String question,
                                       HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return quickQuestionService.updateQuickQuestion(id, userId, question);
    }

    @DeleteMapping("/{id}")
    public ResultVo deleteQuickQuestion(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResultVo.error("请先登录");
        }
        return quickQuestionService.deleteQuickQuestion(id, userId);
    }
}