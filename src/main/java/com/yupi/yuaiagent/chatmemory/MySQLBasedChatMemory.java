package com.yupi.yuaiagent.chatmemory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.yupi.yuaiagent.dao.ConversationMemoryDAO;
import com.yupi.yuaiagent.model.domain.ConversationMemory;
import com.yupi.yuaiagent.model.enmu.MessageTypeEnum;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @date 2025/4/28
 * @description 基于 MySQL 持久化的对话记忆
 */
@Component
public class MySQLBasedChatMemory implements ChatMemory {

    private final ConversationMemoryDAO conversationMemoryDAO;

    public MySQLBasedChatMemory(ConversationMemoryDAO conversationMemoryDAO) {
        this.conversationMemoryDAO = conversationMemoryDAO;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<ConversationMemory> conversationMemories = new ArrayList<>();
        Gson gson = new Gson();
        for (Message message : messages) {
            String messageType = message.getMessageType().getValue();
            String mes = gson.toJson(message);
            ConversationMemory conversationMemory = ConversationMemory.builder().conversationId(conversationId)
                    .type(messageType).memory(mes).build();
            conversationMemories.add(conversationMemory);
        }
        conversationMemoryDAO.saveBatch(conversationMemories);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<ConversationMemory> messages = conversationMemoryDAO.getMessages(conversationId);
        if (CollectionUtil.isEmpty(messages)) {
            return List.of();
        }
        return messages.stream()
                .skip(Math.max(0, messages.size() - lastN))
                .map(this::getMessage)
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        conversationMemoryDAO.deleteMemory(conversationId);
    }

    private Message getMessage(ConversationMemory conversationMemory) {
        String memory = conversationMemory.getMemory();
        Gson gson = new Gson();
        return (Message) gson.fromJson(memory, MessageTypeEnum.fromValue(conversationMemory.getType()).getClazz());
    }
}
