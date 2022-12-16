package com.aldikitta.ktorclientcomposetemplate.util

import com.aldikitta.ktorclientcomposetemplate.data.model.ContentLanguage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigDataSource @Inject constructor(
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val _deviceLanguage: MutableStateFlow<ContentLanguage> =
        MutableStateFlow(getCurrentDeviceLanguage())
    val deviceLanguage: StateFlow<ContentLanguage> = _deviceLanguage.asStateFlow()

    fun updateLocale() {
        externalScope.launch {
            val deviceLanguage = getCurrentDeviceLanguage()
            _deviceLanguage.emit(deviceLanguage)
        }
    }

    private fun getCurrentDeviceLanguage(): ContentLanguage {
        val locale = Locale.getDefault()

        val languageCode = locale.toLanguageTag().ifEmpty { ContentLanguage.default.languageCode }
        val region = locale.country.ifEmpty { ContentLanguage.default.region }

        return ContentLanguage(
            languageCode = languageCode,
            region = region
        )
    }
}