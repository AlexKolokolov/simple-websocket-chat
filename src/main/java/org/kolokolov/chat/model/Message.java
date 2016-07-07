package org.kolokolov.chat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by kolokolov on 7/3/16.
 */

@Entity
@Table(name = "message")
public class Message {

    private static final Gson GSON = new GsonBuilder().
            setDateFormat("dd.MM.yy HH:mm:ss").
            create();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private MessageType type;

    @Column(name = "author")
    private String author;

    @Column(name = "body")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
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
