package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.domain.FeedData

class ReadFeedDataUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke(feedData: FeedData) {
        mainRepository.update(feedData.copy(hasRead = true))
    }
}