package com.campaigns.service

import com.campaigns.configuration.AppProperties
import com.campaigns.repository.CampaignRepository
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CampaignServiceTest {
    @Mock
    private lateinit var appProperties: AppProperties
    @Mock
    private lateinit var campaignRepository: CampaignRepository
    @Mock
    private lateinit var mockedMongoCollection: MongoCollection<Document>
    @Mock
    private lateinit var mockedFindIterable: FindIterable<Document>
    @InjectMocks
    private lateinit var sut: CampaignService

    @Before
    fun setUp() {
        `when`(appProperties.campaignsData).thenReturn("data.json")
        doNothing().`when`(campaignRepository).save(ArgumentMatchers.anyString())
    }

    @Test
    fun `'populateCampaigns' should populate the database with the data provided in the data json file`() {
        sut.populateCampaigns()

        verify(campaignRepository, times(1)).save(ArgumentMatchers.anyString())
    }

    @Test
    fun `'populateCampaigns' shouldn't populate the database when an error occurs reading the json file`() {
        `when`(appProperties.campaignsData).thenReturn("")
        sut.populateCampaigns()

        verify(campaignRepository, times(0)).save(ArgumentMatchers.anyString())
    }

    @Test
    fun `'getCampaigns' should return the data populated in the database`() {
        `when`(campaignRepository.get()).thenReturn(mockedMongoCollection)
        `when`(mockedMongoCollection.count()).thenReturn(1)
        `when`(mockedMongoCollection.find()).thenReturn(mockedFindIterable)
        `when`(mockedFindIterable.first()).thenReturn(getTestMongoDocument())

        val data = sut.getCampaigns() as String
        assertThat(data, `is`("test"))
    }

    @Test
    fun `'getCampaigns' should return an empty string when there are no collections in the database`() {
        `when`(campaignRepository.get()).thenReturn(mockedMongoCollection)
        `when`(mockedMongoCollection.count()).thenReturn(0)

        val data = sut.getCampaigns() as String
        assertThat(data, `is`(""))
    }

    @Test
    fun `'getImage' should retrieve the image stored in the resources when there is an image with that name`() {
        val imagePath = sut.getImage("img1.jpg")

        assertThat(imagePath.path, `is`("images/img1.jpg"))
    }

    private fun getTestMongoDocument(): Document {
        val document = Document()
        document.append("id", "1")
        document.append("data", "test")

        return document
    }
}