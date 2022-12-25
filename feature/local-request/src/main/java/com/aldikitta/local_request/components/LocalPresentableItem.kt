package com.aldikitta.local_request.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.aldikitta.data.model.PresentableItemLocalState
import com.aldikitta.data.model.PresentableItemState
import com.aldikitta.ui.response.ErrorPresentableItem
import com.aldikitta.ui.response.LoadingPresentableItem
import com.aldikitta.ui.theme.Size
import com.aldikitta.ui.theme.sizes

@Composable
fun LocalPresentableItem(
    presentableState: PresentableItemLocalState,
    modifier: Modifier = Modifier,
    size: Size = MaterialTheme.sizes.presentableItemSmall,
    selected: Boolean = false,
    showTitle: Boolean = false,
    transformations: GraphicsLayerScope.() -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(size.width)
            .aspectRatio(size.ratio)
            .graphicsLayer {
                transformations()
            },
        shape = MaterialTheme.shapes.medium,
        border = if (selected) BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        when (presentableState) {
            is PresentableItemLocalState.Loading -> {
                LoadingPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is PresentableItemLocalState.Error -> {
                ErrorPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is PresentableItemLocalState.Result -> {
                LocalResultMovieItem(
                    modifier = Modifier.fillMaxSize(),
                    presentable = presentableState.movie,
                    showTitle = showTitle,
                    onClick = onClick
                )
            }
        }
    }
}