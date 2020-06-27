package com.example.redditproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.FeedData
import com.example.redditproject.common.ScopedViewModel
import com.example.usecases.GetFeedDataByIdUseCase
import com.example.usecases.ReadFeedDataUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val feedId: String,
    private val getFeedDataByIdUseCase: GetFeedDataByIdUseCase,
    private val readFeedDataUseCase: ReadFeedDataUseCase,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    data class UiModel(val feedData: FeedData)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findFeedData()
            return _model
        }

    private fun findFeedData() {
        launch {
            _model.value = UiModel(getFeedDataByIdUseCase.invoke(feedId))
        }
    }

    fun setAsRead(feedData: FeedData) {
        launch {
            readFeedDataUseCase.invoke(feedData)
        }
    }
}