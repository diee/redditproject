package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.domain.FeedData

class DismissFeedDataUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke(feedData: FeedData): FeedData = with(feedData) {
        copy(dismissed = true).also { mainRepository.update(it) }
    }
}