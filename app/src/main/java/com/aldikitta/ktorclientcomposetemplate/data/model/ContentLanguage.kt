package com.aldikitta.ktorclientcomposetemplate.data.model

data class ContentLanguage(
    val region: String,
    val languageCode: String
) {
    companion object {
        val default: ContentLanguage = ContentLanguage(
            region = "US",
            languageCode = "en-US"
        )
    }
}