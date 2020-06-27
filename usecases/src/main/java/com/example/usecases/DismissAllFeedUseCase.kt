package com.example.usecases

import com.example.data.repository.MainRepository

class DismissAllFeedUseCase(private val mainRepository: MainRepository) {
    suspend fun invoke() = mainRepository.dismissAll()
}