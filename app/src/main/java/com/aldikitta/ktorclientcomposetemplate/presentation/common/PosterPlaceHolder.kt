package com.aldikitta.ktorclientcomposetemplate.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aldikitta.ktorclientcomposetemplate.util.defaultPlaceholder

@Composable
fun PosterPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.defaultPlaceholder()
    )
}