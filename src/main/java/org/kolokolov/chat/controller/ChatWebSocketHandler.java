package org.kolokolov.chat.controller;

import org.kolokolov.chat.model.Message;
import org.kolokolov.chat.model.MessageType;
import org.kolokolov.chat.model.UserProfile;
import org.kolokolov.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kolokolov on 5/14/16.
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private UserProfile user;
    private static final Map<String, WebSocketSession> connections = new ConcurrentHashMap<>();

    @Autowired
    MessageService messageService;

    public static Map<String, WebSocketSession> getConnections() {
        return connections;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        this.user = (UserProfile) webSocketSession.getAttributes().get("user");
        connections.put(user.getNickname(), webSocketSession);
        String message = String.format("%s %s", user.getNickname(), "has joined.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
        broadcast(new Message(MessageType.USER_LIST, getLoggedUsersList()).toJson());
        System.out.println(this + " : " + user.getNickname());
        System.out.println(connections);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws Exception {
        connections.remove(user.getNickname());
        String message = String.format("%s %s", user.getNickname(), "has disconnected.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
        broadcast(new Message(MessageType.USER_LIST, getLoggedUsersList()).toJson());
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        broadcast(new Message(MessageType.MESSAGE, message.getPayload(), user.getNickname()).toJson());
    }

    private void broadcast(String msg) {
        for (WebSocketSession session : connections.values()) {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                connections.remove(user.getNickname());
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("%s %s", user.getNickname(), "has been disconnected.");
                Message jsonMessage = new Message(MessageType.MESSAGE, message, "[SERVER]");
                broadcast(jsonMessage.toJson());
                broadcast(new Message(MessageType.USER_LIST, getLoggedUsersList()).toJson());
            }
        }
    }

    private static synchronized String getLoggedUsersList() {
        String userList = "";
        for (String nickname : connections.keySet())
            userList += nickname + "</br>";
        return userList;
    }
}