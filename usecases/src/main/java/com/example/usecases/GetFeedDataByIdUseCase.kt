package com.example.usecases

import com.example.data.repository.MainRepository
import com.example.domain.FeedData

class GetFeedDataByIdUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke(feedId: String): FeedData = mainRepository.getFeeDataById(feedId)
}