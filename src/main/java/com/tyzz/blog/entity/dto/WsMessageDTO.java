package com.tyzz.blog.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import com.tyzz.blog.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * websocket消息实体类
 * @Author: ZhangZhao
 * DateTime: 2022-05-12 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WsMessageDTO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long userId;

    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long timestamp;

    private String avatar;

    private Object message;

    private boolean anonymous = true;

    private MessageType messageType = MessageType.MESSAGE;

    public static WsMessageDTO tipMessage(Object tip) {
        return WsMessageDTO.builder()
                .messageType(MessageType.TIP)
                .message(tip)
                .build();
    }
    public static WsMessageDTO infoMessage(Object info) {
        return WsMessageDTO.builder()
                .messageType(MessageType.INFO)
                .message(info)
                .build();
    }

    public static WsMessageDTO SyncMessage(Object message) {
        return WsMessageDTO.builder()
                .messageType(MessageType.SYNC)
                .message(message.toString())
                .build();
    }
}
