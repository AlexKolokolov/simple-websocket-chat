package org.kolokolov.chat.controller;

import org.kolokolov.chat.model.Message;
import org.kolokolov.chat.model.MessageType;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kolokolov on 5/14/16.
 */
@ServerEndpoint(value = "/websocket/chat/{nickname}")
public class Chat {
    private String nickname;
    private Session session;
    private static final Map<String, Chat> connections = new ConcurrentHashMap<>();

    public static Map<String, Chat> getConnections() {
        return connections;
    }

    @OnOpen
    public void start(Session session, @PathParam("nickname") String nickname) {
        this.nickname = nickname;
        this.session = session;
        connections.put(this.nickname, this);
        broadcast(new Message(MessageType.USER_LIST, getLoggedUsersList()).toJson());

        String message = String.format("%s %s", nickname, "has joined.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
    }

    @OnClose
    public void end() {
        connections.remove(this.nickname);
        broadcast(new Message(MessageType.USER_LIST, getLoggedUsersList()).toJson());
        String message = String.format("%s %s", nickname, "has disconnected.");
        broadcast(new Message(MessageType.MESSAGE, message, "[SERVER]").toJson());
    }

    @OnMessage
    public void incoming(String message) {
        broadcast(new Message(MessageType.MESSAGE, message, nickname).toJson());
    }

    private static void broadcast(String msg) {
        for (Map.Entry<String, Chat> client : connections.entrySet()) {
            try {
                synchronized (client) {
                    client.getValue().session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client.getKey());
                broadcast(new Message(MessageType.MESSAGE, getLoggedUsersList()).toJson());
                try {
                    client.getValue().session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("%s %s", client.getKey(), "has been disconnected.");
                Message jsonMessage = new Message(MessageType.MESSAGE, message, "[SERVER]");
                broadcast(jsonMessage.toJson());
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