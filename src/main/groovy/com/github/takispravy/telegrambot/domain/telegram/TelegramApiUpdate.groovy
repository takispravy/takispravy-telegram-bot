package com.github.takispravy.telegrambot.domain.telegram

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

@EqualsAndHashCode(includes = 'updateId')
@Builder
class TelegramApiUpdate {

    @Id
    String id

    @Indexed
    Long updateId

    TelegramMessage message
}
