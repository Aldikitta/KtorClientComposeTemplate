package com.aldikitta.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aldikitta.ui.util.defaultPlaceholder

@Composable
fun PosterPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.defaultPlaceholder()
    )
}