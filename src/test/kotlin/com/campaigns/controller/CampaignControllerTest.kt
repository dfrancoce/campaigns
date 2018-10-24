package com.campaigns.controller

import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@WebMvcTest(CampaignController::class)
class CampaignControllerTest {
    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val campaignController: CampaignController? = null

    @Before
    fun setUp() {
    }

    @Test
    fun `'getCampaigns' should return the data provided by the service`() {
        val data = "[{id: 1, name: test}]"
        given(campaignController?.getCampaigns()).willReturn(data)

        mvc?.perform(get("/api/campaigns"))
                ?.andExpect(status().isOk)
                ?.andExpect(jsonPath("$[0].name", `is`("test")))
    }

    @Test
    fun `'getImage' should return the image with the name passed as parameter`() {
        val response = MockHttpServletResponse()
        doNothing().`when`(campaignController)?.getImage(response, "img1.jpg")

        mvc?.perform(get("/api/campaigns/images?name=img1.jpg"))?.andExpect(status().isOk)
    }
}