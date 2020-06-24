package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.domain.FeedData

class GetFeedUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke(): List<FeedData> = mainRepository.getFeedTop()
}