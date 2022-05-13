package com.tyzz.blog.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.entity.dto.WsMessageDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * websocket控制器
 * @Author: ZhangZhao
 * DateTime: 2022-05-12 9:26
 */

@Log4j2
@Component
@ServerEndpoint("/message/websocket/{userId}")
public class WebSocketServer {
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
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
     * 新建立一个ws连接的回调
     * @param session 当前session
     * @param userId 用户连接参数id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketMap.put(userId, this);
        log.info("用户：{} 连接成功！当前在线人数：{}", userId, webSocketMap.size());

        // 向所有客户端发送在线人数
        sendOnlineNum();
        // 向当前客户端发送历史聊天记录
        sendHistoryChats();
    }

    /**
     * 首次连接发送所有历史聊天记录
     */
    private void sendHistoryChats() {

    }

    /**
     * 发送所有客户端在线人数
     */
    private void sendOnlineNum() {
        try {
            for (WebSocketServer server : webSocketMap.values()) {
                server.sendMessage(mapper.writeValueAsString(
                        WsMessageDTO.buildInfo(webSocketMap.size())
                ));
            }
        } catch (Exception e) {
            log.error(e);
        }
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
     * 向指定session发送消息
     * @param message 消息内容
     */
    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
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
        log.info("用户 {} 退出！当前在线人数：{}", userId, webSocketMap.size());
        sendOnlineNum();
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
}
