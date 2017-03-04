package com.github.takispravy.telegrambot

import groovyx.net.http.HttpBuilder
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@SpringBootApplication
class Application {

    @Bean
    TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = 1

        threadPoolTaskScheduler
    }

    @Bean
    HttpBuilder telegram() {
        HttpBuilder.configure {
            request.uri = 'https://api.telegram.org/'
        }
    }

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}

