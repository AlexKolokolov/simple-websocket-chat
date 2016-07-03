package org.kolokolov.chat.controller;

import org.kolokolov.chat.model.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by kolokolov on 5/14/16.
 */

@ServerEndpoint(value = "/websocket/chat/{nickname}")
public class Chat {
    private String nickname;
    private Session session;
    private static final Set<Chat> connections = new CopyOnWriteArraySet<>();
    private static String userList = "";

    public String getNickname() {
        return nickname;
    }

    @OnOpen
    public void start(Session session, @PathParam("nickname") String nickname) {
        this.nickname = nickname;
        this.session = session;
        connections.add(this);
        updateUserList();
        broadcast(new Message("userList", userList).toJson());

        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(new Message("message", message).toJson());

    }

    @OnClose
    public void end() {
        connections.remove(this);
        updateUserList();
        broadcast(new Message("userList", userList).toJson());
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcast(new Message("message", message).toJson());
    }

    @OnMessage
    public void incoming(String message) {
        String formattedMessage = String.format("%s : %s", nickname, message);
        broadcast(new Message("message", formattedMessage).toJson());
    }

    private static void broadcast(String msg) {
        for (Chat client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client);
                updateUserList();
                broadcast(new Message("userList", userList).toJson());
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s", client.nickname, "has been disconnected.");
                Message jsonMessage = new Message("message", message);
                broadcast(jsonMessage.toJson());
            }
        }
    }

    private static synchronized void updateUserList() {
        userList = "";
        for (Chat client : connections) {
           userList += client.getNickname() + "</br>";
        }
    }
}
