package com.campaigns.controller

import com.campaigns.service.CampaignService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import javax.servlet.ServletResponse

/**
 * This class provides the endpoint to get the campaigns
 */
@RestController
@RequestMapping("/api")
class CampaignController(private val campaignService: CampaignService) {

    @ApiOperation(value = "Returns all the campaigns from the database")
    @GetMapping("/campaigns")
    @ResponseStatus(HttpStatus.OK)
    fun getCampaigns(): Any? {
        return campaignService.getCampaigns()
    }

    @ApiOperation(value = "Returns the image passed by parameter")
    @GetMapping("/campaigns/images", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getImage(response: ServletResponse, @RequestParam("name") name: String) {
        val imageFile =  campaignService.getImage(name)
        response.contentType = MediaType.IMAGE_JPEG_VALUE
        StreamUtils.copy(imageFile.inputStream, response.outputStream)
    }
}