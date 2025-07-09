document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const newChatBtn = document.getElementById('new-chat-btn');
    const chatList = document.getElementById('chat-list');
    const deleteChatBtns = document.querySelectorAll('.delete-chat');
    const quickQuestions = document.querySelectorAll('.quick-question');
    const messageInput = document.getElementById('message-input');
    const sendBtn = document.getElementById('send-btn');
    const chatMessages = document.getElementById('chat-messages');

    // 自动调整输入框高度
    messageInput.addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
        if (this.scrollHeight > 120) {
            this.style.height = '120px';
            this.style.overflowY = 'auto';
        } else {
            this.style.overflowY = 'hidden';
        }
    });

    // 新建对话
    newChatBtn.addEventListener('click', function() {
        const newChat = document.createElement('div');
        newChat.className = 'chat-item p-3 rounded-lg cursor-pointer hover:bg-gray-100 transition-colors mb-1 active';
        newChat.innerHTML = `
            <div class="flex justify-between items-center">
                <h3 class="font-medium">新对话</h3>
                <button class="delete-chat text-gray-400 hover:text-red-500"><i class="fa fa-times"></i></button>
            </div>
            <p class="text-sm text-gray-500 truncate">开始新的聊天...</p>
        `;

        // 移除其他对话的active状态
        document.querySelectorAll('.chat-item').forEach(item => {
            item.classList.remove('active', 'bg-gray-100');
        });

        // 添加新对话到列表
        chatList.prepend(newChat);

        // 为新对话的删除按钮添加事件
        newChat.querySelector('.delete-chat').addEventListener('click', function(e) {
            e.stopPropagation();
            if (confirm('确定要删除这个对话吗？')) {
                newChat.remove();
            }
        });

        // 点击对话项切换聊天内容
        newChat.addEventListener('click', function() {
            document.querySelectorAll('.chat-item').forEach(item => {
                item.classList.remove('active', 'bg-gray-100');
            });
            this.classList.add('active', 'bg-gray-100');
            // 清空聊天内容（模拟切换对话）
            chatMessages.innerHTML = `
                <div class="text-center mb-6">
                    <span class="bg-gray-200 text-gray-600 text-xs py-1 px-3 rounded-full">新对话</span>
                </div>
            `;
        });

        // 清空聊天内容（模拟切换到新对话）
        chatMessages.innerHTML = `
            <div class="text-center mb-6">
                <span class="bg-gray-200 text-gray-600 text-xs py-1 px-3 rounded-full">新对话</span>
            </div>
        `;
    });

    // 删除对话
    deleteChatBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.stopPropagation();
            const chatItem = this.closest('.chat-item');
            if (confirm('确定要删除这个对话吗？')) {
                chatItem.remove();
            }
        });
    });

    // 点击对话项切换聊天内容
    document.querySelectorAll('.chat-item').forEach(item => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.chat-item').forEach(i => {
                i.classList.remove('active', 'bg-gray-100');
            });
            this.classList.add('active', 'bg-gray-100');
            // 这里可以根据对话ID加载不同的聊天记录
        });
    });

    // 快捷提问
    quickQuestions.forEach(question => {
        question.addEventListener('click', function() {
            const questionText = this.textContent.trim();
            messageInput.value = questionText;
            messageInput.style.height = 'auto';
            messageInput.style.height = (messageInput.scrollHeight) + 'px';
        });
    });

    // 发送消息
    function sendMessage() {
        const message = messageInput.value.trim();
        if (message === '') return;

        // 添加用户消息
        const userMessageDiv = document.createElement('div');
        userMessageDiv.className = 'flex mb-4';
        userMessageDiv.innerHTML = `
            <div class="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center flex-shrink-0 mr-3">
                <i class="fa fa-user text-gray-600"></i>
            </div>
            <div class="max-w-[70%] bg-white p-3 rounded-lg rounded-tl-none shadow-sm">
                <p>${message}</p>
            </div>
        `;
        chatMessages.appendChild(userMessageDiv);

        // 清空输入框
        messageInput.value = '';
        messageInput.style.height = 'auto';

        // 滚动到底部
        chatMessages.scrollTop = chatMessages.scrollHeight;

        // 模拟AI回复
        setTimeout(() => {
            const aiMessageDiv = document.createElement('div');
            aiMessageDiv.className = 'flex mb-4 justify-end';
            aiMessageDiv.innerHTML = `
                <div class="max-w-[70%] bg-primary text-white p-3 rounded-lg rounded-tr-none shadow-sm">
                    <p>这是一条模拟的AI回复。在实际应用中，这里会连接到后端API获取真实的AI响应。</p>
                </div>
                <div class="w-8 h-8 rounded-full bg-primary flex items-center justify-center flex-shrink-0 ml-3">
                    <i class="fa fa-robot"></i>
                </div>
            `;
            chatMessages.appendChild(aiMessageDiv);

            // 滚动到底部
            chatMessages.scrollTop = chatMessages.scrollHeight;
        }, 1000);
    }

    // 点击发送按钮发送消息
    sendBtn.addEventListener('click', sendMessage);

    // 按Enter键发送消息，Shift+Enter换行
    messageInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    });
});