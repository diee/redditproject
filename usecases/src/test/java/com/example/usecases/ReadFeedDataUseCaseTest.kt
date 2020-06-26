package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.testshared.feedData
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReadFeedDataUseCaseTest {

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var readFeedDataUseCase: ReadFeedDataUseCase

    @Before
    fun setUp() {
        readFeedDataUseCase = ReadFeedDataUseCase(mainRepository)
    }

    @Test
    fun `invoke use case call`() {
        runBlocking {
            val mockFeedData = feedData.copy()
            val result = readFeedDataUseCase.invoke(mockFeedData)
            verify(mainRepository).update(result)
        }
    }

    @Test
    fun `change to read status`() {
        runBlocking {
            val mockFeedData = feedData.copy(hasRead = false)
            val result = readFeedDataUseCase.invoke(mockFeedData)
            Assert.assertTrue(result.hasRead)
        }
    }
}