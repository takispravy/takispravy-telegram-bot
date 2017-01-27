package com.github.takispravy.telegrambot.service

import com.github.takispravy.telegrambot.domain.TelegramApiUpdate
import com.github.takispravy.telegrambot.domain.TelegramChat
import com.github.takispravy.telegrambot.domain.TelegramMessage
import com.github.takispravy.telegrambot.domain.TelegramMessageEntity
import com.github.takispravy.telegrambot.domain.TelegramUser
import groovy.util.logging.Slf4j
import groovyx.net.http.HttpBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static groovyx.net.http.NativeHandlers.Parsers.json

@Service
@Slf4j
class TelegramApiService {

    static String TELEGRAM_AUTH_TOKEN = System.getenv('TAKISPRAVY_TELEGRAM_AUTH_TOKEN')

    static String BOT_PATH = "bot${TELEGRAM_AUTH_TOKEN}"

    @Autowired
    HttpBuilder telegram

    Collection<TelegramApiUpdate> getUpdatesSince(long excludingOffset) {
        log.debug "Getting Telegram updates with excludingOffset=${excludingOffset}..."

        long includingOffset = excludingOffset + 1

        def json = telegram.get() {
            request.uri.path = "/${BOT_PATH}/getUpdates"
            request.uri.query = [ offset: includingOffset ]
        }

        return json.result.collect(this.&toTelegramApiUpdate)
    }

    TelegramApiUpdate toTelegramApiUpdate(json) {
        TelegramApiUpdate.builder()
                .updateId(json.update_id)
                .message(toTelegramMessage(json.message))
                .build()
    }

    TelegramMessage toTelegramMessage(json) {
        TelegramMessage.builder()
                .messageId(json.message_id)
                .from(toTelegramUser(json.from))
                .chat(toTelegramChat(json.chat))
                .date(json.date)
                .text(json.text)
                .entities(json.entities?.collect(this.&toTelegramMessageEntity) ?: [])
                .build()
    }

    TelegramUser toTelegramUser(json) {
        TelegramUser.builder()
                .userId(json.id)
                .firstName(json.first_name)
                .lastName(json.last_name)
                .username(json.username)
                .build()
    }

    TelegramChat toTelegramChat(json) {
        TelegramChat.builder()
                .chatId(json.id)
                .firstName(json.first_name)
                .lastName(json.last_name)
                .username(json.username)
                .type(json.type)
                .build()
    }

    TelegramMessageEntity toTelegramMessageEntity(json) {
        TelegramMessageEntity.builder()
                .type(json.type)
                .offset(json.offset)
                .length(json.length)
                .build()
    }
}
