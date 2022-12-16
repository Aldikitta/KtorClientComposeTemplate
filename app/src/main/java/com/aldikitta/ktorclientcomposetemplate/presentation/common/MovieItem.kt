package com.aldikitta.ktorclientcomposetemplate.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aldikitta.ktorclientcomposetemplate.data.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    val spacing = 16.dp
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(spacing)
            .clip(RoundedCornerShape(spacing))
            .shadow(elevation = 1.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(spacing)
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.fullPosterPath)
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(R.drawable.bg_image_placeholder),
                contentDescription = movie.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.weight(0.4f)
            )

            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = spacing)
            ) {
                Text(
                    text = movie.originalTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(spacing))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.size(spacing))

                Text(
                    text = "IMDB ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(spacing))
                        .background(Color.Yellow)
                        .padding(
                            start = spacing,
                            end = spacing,
                            top = spacing,
                            bottom = spacing
                        )
                )
            }
        }
    }
}