package com.example.usecases

import com.example.data.repository.MainRepository
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
class DismissAllFeedUseCaseTest {

    @Mock
    lateinit var mainRepository: MainRepository

    lateinit var dismissAllFeedUseCase: DismissAllFeedUseCase

    @Before
    fun setUp() {
        dismissAllFeedUseCase = DismissAllFeedUseCase(mainRepository)
    }

    @Test
    fun `invoke use case call`() {
        runBlocking {
            dismissAllFeedUseCase.invoke()
            verify(mainRepository).dismissAll()
        }
    }
}