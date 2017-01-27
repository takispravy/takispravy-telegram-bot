package com.github.takispravy.telegrambot.repository

import com.github.takispravy.telegrambot.domain.TelegramApiUpdate
import org.springframework.data.repository.PagingAndSortingRepository

interface TelegramApiUpdateRepository extends PagingAndSortingRepository<TelegramApiUpdate, String> {

    Long findMaxUpdateId()
}