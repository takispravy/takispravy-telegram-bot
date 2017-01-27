package com.github.takispravy.hellobot

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Service
@Slf4j
class HelloBot {

    static String AUTH_TOKEN = System.getenv('TAKISPRAVY_AUTH_TOKEN')

    static int ALREADY_PROCESSED_UPDATE_ID = Integer.parseInt(System.getenv('TAKISPRAVY_ALREADY_PROCESSED_UPDATE_ID') ?: '0')

    static long PROCESSING_DELAY = Long.parseLong(System.getenv('TAKISPRAVY_PROCESSING_DELAY') ?: '1000')

    @Autowired
    TaskExecutor taskExecutor

    @Autowired
    RESTClient telegram = new RESTClient()

    @PostConstruct
    void startBot() {
        taskExecutor.execute({
            int lastProcessedUpdateId = ALREADY_PROCESSED_UPDATE_ID
            while (true) {
                lastProcessedUpdateId = doHelloBot(lastProcessedUpdateId)
                Thread.sleep(PROCESSING_DELAY)
            }
        })
    }

    int doHelloBot(int lastProcessedUpdateId) {
        def updates = telegram.get(path: "/bot${AUTH_TOKEN}/getUpdates", query: [offset: (lastProcessedUpdateId + 1)])

        int processedUpdateId = lastProcessedUpdateId

        updates.data.result.sort({ -it.update_id }).each {
            log.info "Receive message id=${it.update_id} from ${it.message.from.first_name} ${it.message.from.last_name}: ${it.message.text}"

            telegram.get(path: "/bot${AUTH_TOKEN}/sendMessage", query: [chat_id: it.message.from.id, text: "які справи, ${it.message.from.first_name} ${it.message.from.last_name}?"])

            processedUpdateId = it.update_id
        }

        return processedUpdateId
    }
}
