package com.campaigns.service

import com.campaigns.configuration.AppProperties
import com.campaigns.repository.CampaignRepository
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


/**
 * This class is responsible of the interacting with the CampaignRepository to get the data from the database and return
 * it in the controller
 */
@Service
class CampaignService(private val appProperties: AppProperties, private val campaignsRepository: CampaignRepository) {
    companion object : KLogging()

    /**
     * Populates the database with the campaigns stored in the data JSON
     */
    @PostConstruct
    fun populateCampaigns() {
        logger.debug { "CampaignService - populateCampaigns - start" }

        val data = readDataJson()
        if (data.isNotEmpty()) {
            campaignsRepository.save(data)
        }
    }

    /**
     * Returns the list of campaigns
     */
    fun getCampaigns(): Any? {
        logger.debug { "CampaignService - getCampaigns - start" }

        val databaseCampaigns = campaignsRepository.get()

        return if (databaseCampaigns.count() > 0) {
            val document = databaseCampaigns.find().first()
            document["data"]
        } else {
            ""
        }
    }

    /**
     * Returns the image with the name passed by parameter
     */
    fun getImage(name: String): ClassPathResource {
        logger.debug { "CampaignService - getImage - start" }
        return ClassPathResource("images/$name")
    }

    /**
     * Returns the data.json used to populate the database
     */
    private fun readDataJson(): String {
        return try {
            val dataJson = ClassPathResource(appProperties.campaignsData).inputStream
            val objectMapper = jacksonObjectMapper()
            val data = objectMapper.readTree(dataJson)

            data.toString()
        } catch (e: JsonParseException) {
            ""
        }
    }
}