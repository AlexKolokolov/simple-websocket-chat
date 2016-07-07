package org.kolokolov.chat.service;

import org.kolokolov.chat.dao.HibernateDao;
import org.kolokolov.chat.model.Message;
import org.kolokolov.chat.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kolokolov on 7/2/16.
 */
@Component
public class MessageService {

    @Autowired
    private HibernateDao dao;

    public void setDao(HibernateDao dao) {
        this.dao = dao;
    }

    public void saveMessage(Message message) {
        dao.saveMessage(message);
    }
}
