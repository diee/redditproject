package com.example.redditproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.redditproject.ui.main.MainActivity.ClickActionType.OPEN_DETAIL
import com.example.redditproject.ui.main.MainViewModel
import com.example.testshared.feedData
import com.example.usecases.DismissAllFeedUseCase
import com.example.usecases.DismissFeedDataUseCase
import com.example.usecases.GetFeedUseCase
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
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFeedUseCase: GetFeedUseCase

    @Mock
    lateinit var dismissFeedUseCase: DismissFeedDataUseCase

    @Mock
    lateinit var dismissAllFeedUseCase: DismissAllFeedUseCase

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(
            getFeedUseCase,
            dismissFeedUseCase,
            dismissAllFeedUseCase,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `after invoke getFeedTop use case call, loading is shown`() {
        runBlocking {
            val mockedFeed = listOf(feedData.copy())
            whenever(getFeedUseCase.invoke()).thenReturn(mockedFeed)
            vm.model.observeForever(observer)

            vm.getFeedTop()

            verify(observer).onChanged(MainViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `after invoke dismissAll use case call, DismissAll status is called`() {
        runBlocking {
            vm.model.observeForever(observer)
            vm.dismissAll()
            verify(observer).onChanged(MainViewModel.UiModel.DismissAll)
        }
    }

    @Test
    fun `after click feed item, navigation is called`() {
        runBlocking {
            val mockedFeedData = feedData.copy()
            vm.model.observeForever(observer)
            vm.onFeedItemClicked(mockedFeedData, OPEN_DETAIL)
            verify(observer).onChanged(MainViewModel.UiModel.Navigation(mockedFeedData))
        }
    }
}