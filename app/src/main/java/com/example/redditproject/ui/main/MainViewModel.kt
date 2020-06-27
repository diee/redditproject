package com.example.redditproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.FeedData
import com.example.redditproject.common.ScopedViewModel
import com.example.redditproject.ui.main.MainActivity.ClickActionType
import com.example.redditproject.ui.main.MainActivity.ClickActionType.DISMISS_FEED
import com.example.redditproject.ui.main.MainActivity.ClickActionType.OPEN_DETAIL
import com.example.usecases.DismissAllFeedUseCase
import com.example.usecases.DismissFeedDataUseCase
import com.example.usecases.GetFeedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val dismissFeedDataUseCase: DismissFeedDataUseCase,
    private val dismissAllFeedUseCase: DismissAllFeedUseCase,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val feed: List<FeedData>) : UiModel()
        data class Navigation(val feedData: FeedData) : UiModel()
        object DismissAll : UiModel()
    }

    fun getFeedTop() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getFeedUseCase.invoke())
        }
    }

    fun onFeedItemClicked(feedData: FeedData, action: ClickActionType) {
        when (action) {
            OPEN_DETAIL -> _model.value = UiModel.Navigation(feedData)
            DISMISS_FEED -> dismissFeedData(feedData)
        }
    }

    private fun dismissFeedData(feedData: FeedData) {
        launch {
            dismissFeedDataUseCase.invoke(feedData)
        }
    }

    fun dismissAll() {
        launch {
            dismissAllFeedUseCase.invoke()
            _model.value = UiModel.DismissAll
        }
    }
}