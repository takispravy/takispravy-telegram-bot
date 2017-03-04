package com.github.takispravy.telegrambot.repository.telegram

import com.github.takispravy.telegrambot.domain.telegram.TelegramApiUpdate
import org.springframework.data.repository.PagingAndSortingRepository

interface TelegramApiUpdateRepository extends PagingAndSortingRepository<TelegramApiUpdate, String> {
}