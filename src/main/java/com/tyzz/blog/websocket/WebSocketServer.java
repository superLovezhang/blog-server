package com.tyzz.blog.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.entity.dto.WsMessageDTO;
import com.tyzz.blog.service.impl.RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.tyzz.blog.constant.BlogConstant.*;

/**
 * Description:
 * websocket控制器
 * @Author: ZhangZhao
 * DateTime: 2022-05-12 9:26
 */
@Log4j2
@Component
@ServerEndpoint("/message/websocket/{userId}/{username}")
public class WebSocketServer implements ApplicationContextAware {
    private final ObjectMapper mapper = new ObjectMapper();

    private static RedisService redisService;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId;
    /**
     * 接收username
     */
    private String username;


    /**
     * 新建立一个ws连接的回调
     * @param session 当前session
     * @param userId 用户连接参数id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId, @PathParam(value = "username") String username) {
        this.session = session;
        this.userId = userId;
        this.username = username;
        establishConnect(userId);
        afterOpen();
        log.info("用户：{} 连接成功！当前在线人数：{}", userId, webSocketMap.size());
    }

    private void afterOpen() {
        // 向所有客户端发送在线人数
        sendOnlineNum();
        // 向当前客户端发送历史聊天记录
        sendHistoryChats();
        // 向所有客户端发送自己上线通知
        sendOnlineNotification();
    }

    /**
     * 建立一个连接 放入map中
     * @param userId
     */
    private void establishConnect(String userId) {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).close();
            webSocketMap.remove(userId);
        }
        webSocketMap.put(userId, this);
    }

    private void sendOnlineNotification() {
        try {
            String message = mapper.writeValueAsString(
                    WsMessageDTO.tipMessage(
                            String.format(CHAT_ONLINE_NOTIFICATION, username)
                    ));
            sendMessageToAll(message);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }

    private void sendOfflineNotification() {
        try {
            String message = mapper.writeValueAsString(
                    WsMessageDTO.tipMessage(
                            String.format(CHAT_OFFLINE_NOTIFICATION, username)
                    ));
            sendMessageToAll(message);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }

    /**
     * 首次连接发送所有历史聊天记录
     */
    private void sendHistoryChats() {
        sendMessage(searchChatRecords());
    }

    private List<Object> searchChatRecords() {
        return redisService.lGet(CHAT_RECORD, 0, -1);
    }

    /**
     * 发送所有客户端在线人数
     */
    private void sendOnlineNum() {
        try {
            for (WebSocketServer server : webSocketMap.values()) {
                server.sendMessage(mapper.writeValueAsString(
                        WsMessageDTO.infoMessage(webSocketMap.size())
                ));
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 监听客户端消息事件
     * @param message 客户端发送的消息
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            // storage current message to redis
            saveMessage(message);
            sendMessageToAll(message);
        } catch (Exception e) {
            log.error(e);
        }
        log.info("收到用户：{}发送的消息：{}", userId, message);
    }

    private void saveMessage(String message) {
        long size = redisService.lGetListSize(CHAT_RECORD);
        if (size >= DEFAULT_MAX_CHAT_SIZE) {
            redisService.lLPop(CHAT_RECORD);
        }
        redisService.lRPush(CHAT_RECORD, message);
    }

    /**
     * 向当前session发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * 向当前session发送多条消息
     * @param messages 消息内容
     */
    public void sendMessage(List<Object> messages) {
        try {
            session.getBasicRemote().sendText(mapper.
                    writeValueAsString(
                            WsMessageDTO.SyncMessage(messages)
                    ));
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 向全体客户端发送单条消息
     * @param message 消息内容
     */
    public void sendMessageToAll(String message) {
        try {
            for (WebSocketServer server : webSocketMap.values()) {
                server.sendMessage(message);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 向指定userId对应的session发送消息
     * @param message 消息内容
     * @param userId 用户id
     */
    public void sendInfo(String message, String userId) {
        webSocketMap.get(userId).sendMessage(message);
    }

    /**
     * 当前连接关闭触发回调
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(userId);
        sendOnlineNum();
        sendOfflineNotification();
        log.info("用户 {} 退出！当前在线人数：{}", userId, webSocketMap.size());
    }

    /**
     * 显示关闭一个连接
     */
    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * 当前连接异常触发回调
     * @param session 当前session
     * @param error 异常实体
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error);
    }

    /**
     * 多例情况下 注入的依赖只有第一个实例有
     * 所以依靠context获取ioc容器bean
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisService = applicationContext.getBean(RedisService.class);
    }
}
