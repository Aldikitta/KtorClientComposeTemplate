package com.aldikitta.ktorclientcomposetemplate.data.repository

import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun updateLocale()

    fun getDeviceLanguage(): Flow<ContentLanguage>

}