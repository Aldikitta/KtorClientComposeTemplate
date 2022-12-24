package com.aldikitta.data.repository

import com.aldikitta.data.model.ContentLanguage
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun updateLocale()

    fun getDeviceLanguage(): Flow<ContentLanguage>

}