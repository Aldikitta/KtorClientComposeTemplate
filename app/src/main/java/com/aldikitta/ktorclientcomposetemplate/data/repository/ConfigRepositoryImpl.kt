package com.aldikitta.ktorclientcomposetemplate.data.repository

import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import com.aldikitta.ktorclientcomposetemplate.util.ConfigDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {
    override fun updateLocale() {
        return configDataSource.updateLocale()
    }

    override fun getDeviceLanguage(): Flow<ContentLanguage> {
        return configDataSource.deviceLanguage
    }

 }