package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.domain.FeedData

class RefreshFeedUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke(): List<FeedData> = mainRepository.refreshFeed()
}