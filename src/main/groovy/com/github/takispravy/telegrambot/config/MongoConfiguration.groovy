package com.github.takispravy.telegrambot.config

import com.mongodb.Mongo
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories('com.github.takispravy.telegrambot.repository')
class MongoConfiguration extends AbstractMongoConfiguration {

    static String DATABASE_NAME = System.getenv('MONGO_DATABASE_NAME') ?: 'takispravy'
    static String AUTHENTICATION_DATABASE_NAME = System.getenv('MONGO_AUTH_DATABASE_NAME') ?: 'admin'
    static String HOST = System.getenv('MONGO_HOST') ?: 'takispravy_mongo'
    static Integer PORT = System.getenv('MONGO_PORT') ? Integer.parseInt(System.getenv('MONGO_PORT')) : 27017
    static String USERNAME = System.getenv('MONGO_USERNAME') ?: 'telegramBotService'
    static String PASSWORD = System.getenv('MONGO_PASSWORD')

    @Override
    protected String getDatabaseName() {
        DATABASE_NAME
    }

    @Override
    Mongo mongo() throws Exception {
        new MongoClient(new ServerAddress(HOST, PORT), [ MongoCredential.createCredential(USERNAME, AUTHENTICATION_DATABASE_NAME, PASSWORD.toCharArray()) ])
    }

    @Override
    protected String getMappingBasePackage() {
        'com.github.takispravy.telegrambot.domain'
    }
}