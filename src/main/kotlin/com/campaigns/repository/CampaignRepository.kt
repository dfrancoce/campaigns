package com.campaigns.repository

import com.campaigns.configuration.AppProperties
import com.mongodb.client.MongoCollection
import com.mongodb.util.JSON
import mu.KLogging
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

/**
 * This class is responsible of the operations against the database
 */
@Repository
class CampaignRepository(private val appProperties: AppProperties, private val mongoTemplate: MongoTemplate) {
    companion object : KLogging()

    /**
     * Stores the campaigns into the database
     */
    fun save(data: String) {
        logger.debug { "CampaignRepository - save - start" }

        val doc = Document()
        doc.append("_id", ObjectId())
        doc.append("data", JSON.parse(data))

        mongoTemplate.insert(doc, appProperties.collectionName)
    }

    /**
     * Returns the campaigns stored in the database
     */
    fun get(): MongoCollection<Document> {
        logger.debug { "CampaignRepository - get - start" }
        return mongoTemplate.getCollection(appProperties.collectionName)
    }
}