package org.kolokolov.chat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by kolokolov on 7/3/16.
 */
public class Message {

    private static final Gson GSON = new GsonBuilder().
            setDateFormat("dd.MM.yy HH:mm:ss").
            create();

    private long id;
    private MessageType type;
    private String author;
    private String body;
    private Date created;

    public Message(MessageType type, String body) {
        this.type = type;
        this.body = body;
    }

    public Message(MessageType type, String body, String author) {
        this.type = type;
        this.body = body;
        this.author = author;
        this.created = new Date();
    }

    public String toJson() {
        return GSON.toJson(this, Message.class);
    }
}
