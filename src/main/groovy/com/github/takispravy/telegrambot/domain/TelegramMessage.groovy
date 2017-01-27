package com.github.takispravy.telegrambot.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@EqualsAndHashCode(includes = [ 'messageId', 'from', 'chat' ])
@Builder
class TelegramMessage {

    Long messageId

    TelegramUser from

    TelegramChat chat

    Long date

    String text

    Collection<TelegramMessageEntity> entities
}
