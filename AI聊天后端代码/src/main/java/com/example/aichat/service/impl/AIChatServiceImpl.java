package com.example.aichat.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.aichat.service.AIChatService;
import com.example.aichat.vo.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AIChatServiceImpl implements AIChatService {

    @Value("${ai.deepseek.api-key}")
    private String apiKey;

    @Value("${ai.deepseek.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResultVo getAIResponse(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", 1024);
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String aiResponse = jsonNode.get("content").asText();
                return ResultVo.success(aiResponse);
            } else {
                return ResultVo.error("AI请求失败: " + response.getStatusCode());
            }
        } catch (Exception e) {
            return ResultVo.error("AI服务异常: " + e.getMessage());
        }
    }

    @Override
    public ResultVo getStreamAIResponse(String prompt) {
        SseEmitter emitter = new SseEmitter(TimeUnit.MINUTES.toMillis(5));

        new Thread(() -> {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + apiKey);

                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("prompt", prompt);
                requestBody.put("max_tokens", 1024);
                requestBody.put("temperature", 0.7);
                requestBody.put("stream", true);

                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

                // 模拟流式响应（实际项目中需使用支持流式响应的HTTP客户端）
                // 此处为简化示例，实际应使用像OkHttp这样的客户端处理流式响应
                emitter.send(SseEmitter.event().data("思考中..."));
                Thread.sleep(1000);

                // 模拟AI分块响应
                String[] chunks = {"这是", "一个", "流式", "响应", "示例。"};
                for (String chunk : chunks) {
                    emitter.send(SseEmitter.event().data(chunk));
                    Thread.sleep(500);
                }

                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return ResultVo.success(emitter);
    }
}