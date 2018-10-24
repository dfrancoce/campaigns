package com.campaigns

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CampaignsApplication

fun main(args: Array<String>) {
    runApplication<CampaignsApplication>(*args)
}