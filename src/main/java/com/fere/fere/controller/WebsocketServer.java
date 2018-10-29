package com.fere.fere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{uid}")
@Component
public class WebsocketServer {
    static Logger logger = LoggerFactory.getLogger(WebsocketServer.class);
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebsocketServer> webSocketSet = new ConcurrentHashMap<>();
    private Session session;
    private String uid="";
    @OnOpen
    public void onOpen(Session session,@PathParam("uid") String uid) {
        this.session = session;
        webSocketSet.put(uid, this);    //加入set中
        addOnlineCount();
        //在线数加1
        logger.info("有新窗口开始监听:"+uid+",当前在线人数为" + getOnlineCount());
        this.uid=uid;
        try {
            sendMessage("success");
        } catch (IOException e) {
            logger.error("websocket IO异常");
        }
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);
        JSONObject msgJson=JSON.parseObject(message);
        String sendUserId=msgJson.getString("uid");
        try {
            sendtoUser(message,sendUserId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("error");
        error.printStackTrace();
    }
    public void sendtoUser(String message,String sendUserId) throws IOException {
        if (webSocketSet.get(sendUserId) != null) {
            webSocketSet.get(sendUserId).sendMessage(message);
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",uid);
        }
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public static synchronized void addOnlineCount() {
        WebsocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebsocketServer.onlineCount--;
    }

    public static int getOnlineCount() {
        return onlineCount;
    }
    public static void setOnlineCount(int onlineCount) {
        WebsocketServer.onlineCount = onlineCount;
    }
}
