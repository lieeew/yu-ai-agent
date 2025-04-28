package com.yupi.yuaiagent.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuaiagent.mapper.ConversationMemoryMapper;
import com.yupi.yuaiagent.model.domain.ConversationMemory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @date 2025/4/28
 * @description
 */
@Component
public class ConversationMemoryDAO extends ServiceImpl<ConversationMemoryMapper, ConversationMemory> {


    public List<ConversationMemory> getMessages(String conversationId) {
        return this.lambdaQuery()
                .eq(ConversationMemory::getConversationId, conversationId)
                .list();
    }

    public boolean deleteMemory(String conversationId) {
        return this.lambdaUpdate()
                .eq(ConversationMemory::getConversationId, conversationId)
                .remove();
    }
}
