import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChatServiceTest {

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private ChatServiceImpl chatService;

    @Test
    void processMessage_ShouldSaveToDatabase() {
        // 模拟AI处理返回
        ChatMessage testMessage = new ChatMessage("你好");
        
        when(messageMapper.insert(any())).thenReturn(1);
        
        ChatMessage result = chatService.processMessage(testMessage);
        
        verify(messageMapper, times(1)).insert(testMessage);
        assertNotNull(result.getResponse());
    }
}