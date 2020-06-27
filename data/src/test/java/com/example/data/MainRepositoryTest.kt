package com.example.data

import com.example.data.repository.MainRepository
import com.example.data.source.LocalDataSource
import com.example.data.source.RemoteDataSource
import com.example.testshared.feedData
import com.nhaarman.mockitokotlin2.verify
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

    @Mock
    lateinit var localDataSource: LocalDataSource

    lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        mainRepository = MainRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `get feed from local data source first`() {
        runBlocking {
            val mockFeedData = listOf(feedData.copy())
            whenever(localDataSource.getFeed()).thenReturn(mockFeedData)

            val result = mainRepository.getFeedTop()

            Assert.assertEquals(mockFeedData, result)
        }
    }

    @Test
    fun `getFeedById calls local data source`() {
        runBlocking {

            val feedData = feedData.copy("id_test")
            whenever(localDataSource.getFeedDataById("id_test")).thenReturn(feedData)

            val result = mainRepository.getFeeDataById("id_test")

            Assert.assertEquals(feedData, result)
        }
    }

    @Test
    fun `update feed data local data source`() {
        runBlocking {

            val mockedFeedData = feedData.copy()

            mainRepository.update(mockedFeedData)

            verify(localDataSource).updateFeedData(mockedFeedData)
        }
    }
}