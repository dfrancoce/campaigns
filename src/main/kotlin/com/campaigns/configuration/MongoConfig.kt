package com.campaigns.configuration

import com.mongodb.MongoClient
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import java.io.IOException

@Configuration
class MongoConfig {
    private val url = "localhost"
    private val name = "campaigns"

    @Bean
    @Throws(IOException::class)
    fun mongoTemplate(): MongoTemplate {
        val mongo = EmbeddedMongoFactoryBean()
        mongo.bindIp = url

        val mongoClient = mongo.getObject() as MongoClient
        return MongoTemplate(mongoClient, name)
    }
}