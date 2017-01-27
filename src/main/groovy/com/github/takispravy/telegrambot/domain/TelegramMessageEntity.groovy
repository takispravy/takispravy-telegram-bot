package com.github.takispravy.telegrambot.domain

import groovy.transform.builder.Builder

@Builder
class TelegramMessageEntity {

    String type

    Integer offset

    Integer length
}
