package com.github.takispravy.telegrambot.domain.telegram

import groovy.transform.builder.Builder

@Builder
class TelegramMessageEntity {

    String type

    Integer offset

    Integer length
}
