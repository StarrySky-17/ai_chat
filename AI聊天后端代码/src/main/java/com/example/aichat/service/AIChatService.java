package com.example.aichat.service;

import com.example.aichat.vo.ResultVo;

public interface AIChatService {

    ResultVo getAIResponse(String prompt);

    ResultVo getStreamAIResponse(String prompt);

}