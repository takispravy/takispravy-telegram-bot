package com.github.takispravy.telegrambot.controller

import com.github.takispravy.telegrambot.domain.listeners.MessageListener
import com.github.takispravy.telegrambot.service.MessageListenersService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class MessageListenersController {

    @Autowired
    MessageListenersService messageListenersService

    @PostMapping(path = '/listener')
    @ResponseBody MessageListener registerNew(@RequestBody MessageListener messageListener) {
        log.info "Receive request to register new message listener: ${messageListener}"

        messageListenersService.registerNew messageListener
    }
}
