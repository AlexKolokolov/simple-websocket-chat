package org.kolokolov.chat.controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
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

    @OnOpen
    public void start(Session session, @PathParam("nickname") String nickname) {
        this.nickname = nickname;
        this.session = session;
        connections.add(this);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }

    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcast(message);
    }

    @OnMessage
    public void incoming(String message) {
        String formattedMessage = String.format("%s : %s", nickname, message);
        broadcast(formattedMessage);
    }

    private static void broadcast(String msg) {
        for (Chat client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s", client.nickname, "has been disconnected.");
                broadcast(message);
            }
        }
    }
}
