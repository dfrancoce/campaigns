package com.campaigns.repository

import com.campaigns.configuration.AppProperties
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.mongodb.core.MongoTemplate

@RunWith(MockitoJUnitRunner::class)
class CampaignRepositoryTest {
    @Mock
    private lateinit var mongoTemplate: MongoTemplate
    @Mock
    private lateinit var appProperties: AppProperties
    @Mock
    private lateinit var mockedMongoCollection: MongoCollection<Document>
    @InjectMocks
    private lateinit var sut: CampaignRepository

    @Before
    fun setUp() {
        `when`(appProperties.collectionName).thenReturn("campaigns")
        `when`(mongoTemplate.getCollection("campaigns")).thenReturn(mockedMongoCollection)
    }

    @Test
    fun `'save' should store the init data into the database`() {
        sut.save(getTestData())

        verify(appProperties, times(1)).collectionName
    }

    @Test
    fun `'get' should obtain the data stored in the database`() {
        assertThat(sut.get(), notNullValue())
    }

    private fun getTestData(): String {
        val doc = Document()
        doc.append("id", "1")
        doc.append("name", "test")

        return doc.toJson()
    }
}