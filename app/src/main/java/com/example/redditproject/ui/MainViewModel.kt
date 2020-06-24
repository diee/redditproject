package com.example.redditproject.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usecases.GetFeedUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getFeedUseCase: GetFeedUseCase) : ViewModel() {

    fun getFeedTop() {
        viewModelScope.launch {
            val response = getFeedUseCase.invoke()
            Log.v("Service response", response[0].toString())
        }
    }
}