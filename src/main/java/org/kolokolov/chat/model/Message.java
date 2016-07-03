package org.kolokolov.chat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by kolokolov on 7/3/16.
 */
public class Message {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private String type;
    private String body;

    public Message(String type, String body) {
        this.type = type;
        this.body = body;
    }

    public String toJson() {
        return GSON.toJson(this, Message.class);
    }
}
