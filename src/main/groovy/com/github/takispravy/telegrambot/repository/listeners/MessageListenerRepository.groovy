package com.github.takispravy.telegrambot.repository.listeners

import com.github.takispravy.telegrambot.domain.listeners.MessageListener
import org.springframework.data.repository.PagingAndSortingRepository

interface MessageListenerRepository extends PagingAndSortingRepository<MessageListener, String> {

    MessageListener findOneByName(String name)
}