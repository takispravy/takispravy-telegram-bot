package com.github.takispravy.telegrambot.domain.telegram

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@EqualsAndHashCode(includes = 'userId')
@Builder
class TelegramUser {

    Long userId

    String firstName

    String lastName

    String username
}
