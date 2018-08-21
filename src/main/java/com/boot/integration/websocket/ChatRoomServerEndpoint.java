package com.boot.integration.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.boot.integration.websocket.WebSocketUtils.LIVING_SESSIONS_CACHE;
import static com.boot.integration.websocket.WebSocketUtils.sendMessage;
import static com.boot.integration.websocket.WebSocketUtils.sendMessageAll;

/**
 * 聊天室
 *
 * @author Levin
 * @since 2018/6/26 0026
 */
@RestController
@ServerEndpoint("/chat-room/{username}")
public class ChatRoomServerEndpoint
{

    private static final Logger log = LoggerFactory.getLogger(ChatRoomServerEndpoint.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制

    @OnOpen
    public void openSession(@PathParam("username") String username, Session session)
    {
        LIVING_SESSIONS_CACHE.put(username, session);

        String joinMessage = "欢迎用户[" + username + "] 来到聊天室！";

        log.info("[{}] - [{}]", joinMessage, dateFormat.format(new Date()));

        sendMessageAll(joinMessage);
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, String message)
    {

        String newMessage = "用户[" + username + "] : " + message;

        log.info("[{}] - [{}]", newMessage, dateFormat.format(new Date()));

        sendMessageAll(newMessage);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session)
    {

        LIVING_SESSIONS_CACHE.remove(username);

        String exitMessage = "用户[" + username + "] 已经离开聊天室了！";

        log.info("[{}] - [{}]", exitMessage, dateFormat.format(new Date()));

        sendMessageAll(exitMessage);

        try
        {
            session.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        try
        {
            session.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    @GetMapping("/chat-room/{sender}/to/{receive}")
    public void onMessage(@PathVariable("sender") String sender, @PathVariable("receive") String receive, String message)
    {
        //一对一发送时将信息反馈给指定接收人
        sendMessage(LIVING_SESSIONS_CACHE.get(sender), "[" + sender + "]" + "-> [" + receive + "] : " + message);
        sendMessage(LIVING_SESSIONS_CACHE.get(receive), "[" + sender + "]" + "-> [" + receive + "] : " + message);
    }

}
