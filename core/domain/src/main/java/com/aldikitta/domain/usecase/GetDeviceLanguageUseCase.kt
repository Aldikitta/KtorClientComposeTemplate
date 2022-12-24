package com.aldikitta.domain.usecase

import com.aldikitta.data.model.ContentLanguage
import com.aldikitta.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceLanguageUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<ContentLanguage> {
        return configRepository.getDeviceLanguage()
    }
}