package com.example.redditproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.FeedData
import com.example.redditproject.ui.main.MainActivity.*
import com.example.redditproject.ui.main.MainActivity.ClickActionType.*
import com.example.usecases.DismissAllFeedUseCase
import com.example.usecases.DismissFeedDataUseCase
import com.example.usecases.GetFeedUseCase
import com.example.usecases.RefreshFeedUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val refreshFeedUseCase: RefreshFeedUseCase,
    private val dismissFeedDataUseCase: DismissFeedDataUseCase,
    private val dismissAllFeedUseCase: DismissAllFeedUseCase
) : ViewModel() {

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
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getFeedUseCase.invoke())
        }
    }

    fun refreshFeed() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(refreshFeedUseCase.invoke())
        }
    }

    fun onFeedItemClicked(feedData: FeedData, action: ClickActionType) {
        when (action) {
            OPEN_DETAIL -> _model.value = UiModel.Navigation(feedData)
            DISMISS_FEED -> dismissFeedData(feedData)
        }
    }

    private fun dismissFeedData(feedData: FeedData) {
        viewModelScope.launch {
            dismissFeedDataUseCase.invoke(feedData)
        }
    }

    fun dismissAll() {
        viewModelScope.launch {
            dismissAllFeedUseCase.invoke()
            _model.value = UiModel.DismissAll
        }
    }
}