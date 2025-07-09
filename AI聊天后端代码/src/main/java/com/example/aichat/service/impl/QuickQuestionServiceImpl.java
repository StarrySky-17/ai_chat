package com.example.aichat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aichat.entity.QuickQuestion;
import com.example.aichat.mapper.QuickQuestionMapper;
import com.example.aichat.service.QuickQuestionService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuickQuestionServiceImpl extends ServiceImpl<QuickQuestionMapper, QuickQuestion> implements QuickQuestionService {

    @Autowired
    private QuickQuestionMapper quickQuestionMapper;

    @Override
    public ResultVo addQuickQuestion(Long userId, String question) {
        QuickQuestion quickQuestion = new QuickQuestion();
        quickQuestion.setUserId(userId);
        quickQuestion.setQuestion(question);
        quickQuestion.setCreateTime(LocalDateTime.now());
        quickQuestion.setStatus(1); // 正常状态

        int rows = quickQuestionMapper.insert(quickQuestion);
        if (rows > 0) {
            return ResultVo.success(quickQuestion);
        } else {
            return ResultVo.error("添加快捷提问失败");
        }
    }

    @Override
    public ResultVo getQuickQuestionsByUserId(Long userId) {
        List<QuickQuestion> questions = quickQuestionMapper.selectByUserId(userId);
        return ResultVo.success(questions);
    }

    @Override
    public ResultVo updateQuickQuestion(Long id, Long userId, String question) {
        QuickQuestion quickQuestion = quickQuestionMapper.selectById(id);
        if (quickQuestion == null) {
            return ResultVo.error("快捷提问不存在");
        }
        if (!quickQuestion.getUserId().equals(userId)) {
            return ResultVo.error("无权修改该快捷提问");
        }

        quickQuestion.setQuestion(question);
        int rows = quickQuestionMapper.updateById(quickQuestion);
        if (rows > 0) {
            return ResultVo.success("更新成功");
        } else {
            return ResultVo.error("更新失败");
        }
    }

    @Override
    public ResultVo deleteQuickQuestion(Long id, Long userId) {
        QuickQuestion quickQuestion = quickQuestionMapper.selectById(id);
        if (quickQuestion == null) {
            return ResultVo.error("快捷提问不存在");
        }
        if (!quickQuestion.getUserId().equals(userId)) {
            return ResultVo.error("无权删除该快捷提问");
        }

        // 逻辑删除
        quickQuestion.setStatus(0);
        int rows = quickQuestionMapper.updateById(quickQuestion);
        if (rows > 0) {
            return ResultVo.success("删除成功");
        } else {
            return ResultVo.error("删除失败");
        }
    }
}