package com.example.usecases

import com.example.data.repository.MainRepository
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
class GetFeedUseCaseTest() {

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var getFeedUseCase: GetFeedUseCase

    @Before
    fun setUp() {
        getFeedUseCase = GetFeedUseCase(mainRepository)
    }

    @Test
    fun `invoke use case call`() {
        runBlocking {

            val mockFeedData = listOf(feedData.copy())
            whenever(mainRepository.getFeedTop()).thenReturn(mockFeedData)

            val result = getFeedUseCase.invoke()

            Assert.assertEquals(mockFeedData, result)
        }
    }

}