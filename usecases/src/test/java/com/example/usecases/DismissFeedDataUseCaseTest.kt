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
class DismissFeedDataUseCaseTest {

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var dismissFeedDataUseCase: DismissFeedDataUseCase

    @Before
    fun setUp() {
        dismissFeedDataUseCase = DismissFeedDataUseCase(mainRepository)
    }

    @Test
    fun `invoke use case call`() {
        runBlocking {
            val mockFeedData = feedData.copy()
            val result = dismissFeedDataUseCase.invoke(mockFeedData)
            verify(mainRepository).update(result)
        }
    }

    @Test
    fun `change to dismiss status`() {
        runBlocking {
            val mockFeedData = feedData.copy(dismissed = false)
            val result = dismissFeedDataUseCase.invoke(mockFeedData)
            Assert.assertTrue(result.dismissed)
        }
    }
}