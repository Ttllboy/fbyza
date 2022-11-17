package com.ruoyi.zayy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/zayyWebsocket") // 接口路径 ws://localhost:8084/zayyWebsocket
@Slf4j
public class WebSocket {
    //实例一个session，这个session是websocket的session
    private Session session;

    //存放websocket的集合（本次demo不会用到，聊天室的demo会用到）
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }
    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    //新增一个方法用于主动向客户端发送消息
    public static void sendMessage(String message) {
        for (WebSocket webSocket: webSocketSet) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
