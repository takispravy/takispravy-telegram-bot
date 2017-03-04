package com.github.takispravy.telegrambot.service

import com.github.takispravy.telegrambot.domain.listeners.MessageListener
import com.github.takispravy.telegrambot.domain.telegram.TelegramApiUpdate
import com.github.takispravy.telegrambot.repository.listeners.MessageListenerRepository
import groovy.util.logging.Slf4j
import groovyx.net.http.HttpBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class MessageListenersService {

    @Autowired
    MessageListenerRepository messageListenerRepository

    MessageListener registerNew(MessageListener messageListener) {
        if (messageListenerRepository.findOneByName(messageListener.name) != null) {
            log.warn("Can't create message listener with name ${messageListener.name}: already exists")
            throw new IllegalArgumentException("Can't create message listener with name ${messageListener.name}: already exists")
        }

        return messageListenerRepository.save(messageListener)
    }

    void notifyAllListenersAbout(Collection<TelegramApiUpdate> telegramApiUpdates) {
        Collection<MessageListener> listeners = messageListenerRepository.findAll()
        telegramApiUpdates.each { TelegramApiUpdate telegramApiUpdate ->
            listeners.each { MessageListener messageListener ->
                HttpBuilder.configure {
                    request.uri = messageListener.callback
                }.post() {
                    request.body = telegramApiUpdate
                }
            }
        }
    }
}
