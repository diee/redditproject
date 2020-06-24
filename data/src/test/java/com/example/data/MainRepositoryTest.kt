package com.example.data

import com.example.data.repository.MainRepository
import com.example.data.source.RemoteDataSource
import com.example.testshared.feedData
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        mainRepository = MainRepository(remoteDataSource)
    }

    @Test
    fun `get feed from server`() {
        runBlocking {
            val mockFeedData = listOf(feedData.copy())
            whenever(remoteDataSource.getFeedTop()).thenReturn(mockFeedData)

            val result = mainRepository.getFeedTop()

            Assert.assertEquals(mockFeedData, result)
        }
    }
}