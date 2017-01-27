package com.github.takispravy.telegrambot.service

import com.github.takispravy.telegrambot.domain.TelegramApiUpdate
import com.github.takispravy.telegrambot.repository.TelegramApiUpdateRepository
import groovy.util.logging.Slf4j
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

import static java.lang.Math.max

@Scope('singleton')
@Service
@Slf4j
class TelegramApiUpdatesService {

    static final PeriodFormatter TIME_LIMIT_FORMAT = new PeriodFormatterBuilder()
            .appendHours().appendSuffix('h')
            .appendMinutes().appendSuffix('min')
            .appendSeconds().appendSuffix('sec')
            .toFormatter()

    static String GET_UPDATES_DELAY = System.getenv('GET_UPDATES_DELAY') ?: '10sec'

    @Autowired
    TelegramApiUpdateRepository telegramApiUpdateRepository

    @Autowired
    TelegramApiService telegramApiService

    @Autowired
    TaskScheduler taskScheduler

    Long lastIngestedUpdateId

    @PostConstruct
    void scheduleGettingNewUpdates() {
        log.info 'Starting Telegram API updates service...'

        lastIngestedUpdateId = telegramApiUpdateRepository.findMaxUpdateId() ?: 0
        log.info "Last ingested update_id=$lastIngestedUpdateId"

        log.info "Scheduling getting new updates with fixed delay ${GET_UPDATES_DELAY}..."
        taskScheduler.scheduleWithFixedDelay(this.&getNewUpdates, fromDurationString(GET_UPDATES_DELAY))
    }

    void getNewUpdates() {
        log.info "Getting new updates since update_id=${lastIngestedUpdateId} from Telegram API..."
        Collection<TelegramApiUpdate> newUpdates = telegramApiService.getUpdatesSince(lastIngestedUpdateId)

        log.info "Received ${newUpdates.size()} new updates" + (!newUpdates.empty ? ', saving it to database' : '') + '...'
        telegramApiUpdateRepository.save newUpdates

        lastIngestedUpdateId = max(lastIngestedUpdateId, newUpdates.collect({ it.updateId }).max() ?: 0)
    }

    static long fromDurationString(String durationAsString) {
        TIME_LIMIT_FORMAT.parsePeriod(durationAsString).toStandardDuration().getStandardSeconds() * 1000
    }
}
