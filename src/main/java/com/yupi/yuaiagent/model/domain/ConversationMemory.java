package com.yupi.yuaiagent.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName conversation_memory
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="conversation_memory")
public class ConversationMemory implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "conversation_id")
    private String conversationId;

    /**
     * @see org.springframework.ai.chat.messages.MessageType
     */
    @TableField(value = "type")
    private String type;

    /**
     *
     */
    @TableField(value = "memory")
    private String memory;

    /**
     *
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     *
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     *
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}