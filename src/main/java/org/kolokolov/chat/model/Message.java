package org.kolokolov.chat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kolokolov on 7/3/16.
 */
public class Message {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

    private long id;
    private MessageType type;
    private String author;
    private String body;
    private String textDate;
    private Date created;

    public Message(MessageType type, String body) {
        this.type = type;
        this.body = body;
        this.created = new Date();
    }

    public Message(MessageType type, String body, String author) {
        this.type = type;
        this.body = body;
        this.author = author;
        this.created = new Date();
    }

    public String toJson() {
        this.textDate = DATE_FORMAT.format(created);
        return GSON.toJson(this, Message.class);
    }
}
