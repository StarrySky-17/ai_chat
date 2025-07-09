package com.example.aichat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aichat.entity.QuickQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuickQuestionMapper extends BaseMapper<QuickQuestion> {

    List<QuickQuestion> selectByUserId(@Param("userId") Long userId);

}