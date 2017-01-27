package com.github.takispravy.telegrambot.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@EqualsAndHashCode(includes = 'chatId')
@Builder
class TelegramChat {

    Long chatId

    String firstName

    String lastName

    String username

    String type
}
