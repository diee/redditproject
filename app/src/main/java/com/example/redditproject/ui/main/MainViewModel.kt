package com.example.redditproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.FeedData
import com.example.usecases.GetFeedUseCase
import com.example.usecases.RefreshFeedUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getFeedUseCase: GetFeedUseCase,
                    private val refreshFeedUseCase: RefreshFeedUseCase) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val feed: List<FeedData>) : UiModel()
    }

    fun getFeedTop() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getFeedUseCase.invoke())
        }
    }

    fun refreshFeed(){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(refreshFeedUseCase.invoke())
        }
    }
}