package com.example.redditproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.FeedData
import com.example.usecases.GetFeedDataByIdUseCase
import com.example.usecases.ReadFeedDataUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val feedId: String,
    private val getFeedDataByIdUseCase: GetFeedDataByIdUseCase,
    private val readFeedDataUseCase: ReadFeedDataUseCase
) : ViewModel() {

    data class UiModel(val feedData: FeedData)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findFeedData()
            return _model
        }

    private fun findFeedData() {
        viewModelScope.launch {
            _model.value = UiModel(getFeedDataByIdUseCase.invoke(feedId))
        }
    }

    fun setAsRead() {
        viewModelScope.launch {
            _model.value?.feedData?.let { readFeedDataUseCase.invoke(it) }
        }
    }
}