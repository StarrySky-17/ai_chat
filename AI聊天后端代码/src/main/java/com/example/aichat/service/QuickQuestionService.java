package com.example.aichat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aichat.entity.QuickQuestion;
import com.example.aichat.vo.ResultVo;

import java.util.List;

public interface QuickQuestionService extends IService<QuickQuestion> {

    ResultVo addQuickQuestion(Long userId, String question);

    ResultVo getQuickQuestionsByUserId(Long userId);

    ResultVo updateQuickQuestion(Long id, Long userId, String question);

    ResultVo deleteQuickQuestion(Long id, Long userId);

}