import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatApplicationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void sendMessage_ShouldReturnStreamingResponse() {
        String response = restTemplate.postForObject("/api/chat", 
            "{\"content\":\"你好\"}", 
            String.class);
        
        assertTrue(response.contains("msgId"));
        assertTrue(response.contains("content"));
    }
}