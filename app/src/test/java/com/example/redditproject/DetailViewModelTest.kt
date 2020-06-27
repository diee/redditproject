package com.example.redditproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.redditproject.ui.detail.DetailViewModel
import com.example.testshared.feedData
import com.example.usecases.GetFeedDataByIdUseCase
import com.example.usecases.ReadFeedDataUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFeedDataByIdUseCase: GetFeedDataByIdUseCase

    @Mock
    lateinit var readFeedDataUseCase: ReadFeedDataUseCase

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel("h_123", getFeedDataByIdUseCase, readFeedDataUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `observing feed liveData on getFeedById use case call`() {
        runBlocking {
            val mockedFeedData = feedData.copy("h_123")
            whenever(getFeedDataByIdUseCase.invoke("h_123")).thenReturn(mockedFeedData)

            vm.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel(mockedFeedData))
        }
    }

    @Test
    fun `when feed opened, the readFeedDataUseCase use case is invoked`() {
        runBlocking {
            val mockedFeedData = feedData.copy("h_123")
            whenever(readFeedDataUseCase.invoke(mockedFeedData)).thenReturn(Unit)
            vm.setAsRead(mockedFeedData)
            verify(readFeedDataUseCase).invoke(mockedFeedData)
        }
    }
}