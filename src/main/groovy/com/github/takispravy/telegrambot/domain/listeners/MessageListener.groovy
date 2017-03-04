package com.github.takispravy.telegrambot.domain.listeners

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

@EqualsAndHashCode(includes = 'name')
@ToString(includeNames = true, ignoreNulls = true)
class MessageListener {

    @Id
    String id

    @Indexed
    String name

    String callback
}
