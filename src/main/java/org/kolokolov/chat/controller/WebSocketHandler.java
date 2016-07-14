package org.kolokolov.chat.controller;

import org.kolokolov.chat.model.Message;
import org.kolokolov.chat.model.MessageType;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kolokolov on 5/14/16.
 */
public class WebSocketHandler extends TextWebSocketHandler {
    private String nickname = "NoName";
    private static final List<WebSocketSession> connections = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connections.add(session);
        String message = String.format("%s %s", nickname, "has joined.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connections.remove(session);
        String message = String.format("%s %s", nickname, "has disconnected.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        broadcast(new Message(MessageType.MESSAGE, message.toString(), nickname).toJson());
    }

    private static void broadcast(String msg) {
        for (WebSocketSession session : connections) {
            try {
                synchronized (session) {
                    session.sendMessage(new TextMessage(msg));
                }
            } catch (IOException e) {
                connections.remove(session);
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("%s %s", "NoName", "has been disconnected.");
                Message jsonMessage = new Message(MessageType.MESSAGE, message, "[SERVER]");
                broadcast(jsonMessage.toJson());
            }
        }
    }

//    private static synchronized String getLoggedUsersList() {
//        String userList = "";
//        for (String nickname : connections.keySet())
//            userList += nickname + "</br>";
//        return userList;
//    }
}