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
class GetFeedDataByIdUseCaseTest {

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var getFeedDataByIdUseCase: GetFeedDataByIdUseCase

    @Before
    fun setUp() {
        getFeedDataByIdUseCase = GetFeedDataByIdUseCase(mainRepository)
    }

    @Test
    fun `invoke use case call`() {
        runBlocking {

            val feedMock = feedData.copy("id_test")
            whenever(mainRepository.getFeeDataById("id_test")).thenReturn(feedMock)

            val result = getFeedDataByIdUseCase.invoke("id_test")

            Assert.assertEquals(feedMock, result)
        }
    }
}