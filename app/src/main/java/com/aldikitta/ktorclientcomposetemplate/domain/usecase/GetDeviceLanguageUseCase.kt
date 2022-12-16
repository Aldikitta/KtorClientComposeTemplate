package com.aldikitta.ktorclientcomposetemplate.domain.usecase

import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.data.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceLanguageUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<ContentLanguage> {
        return configRepository.getDeviceLanguage()
    }
}