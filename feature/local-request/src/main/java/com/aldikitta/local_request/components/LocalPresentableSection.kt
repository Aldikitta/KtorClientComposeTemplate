package com.aldikitta.local_request.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.aldikitta.data.local.model.MovieEntity
import com.aldikitta.local_request.PresentableLocalItemState
import com.aldikitta.ui.MovplayScrollToTopButton
import com.aldikitta.ui.gridVerticalScrollBar
import com.aldikitta.ui.theme.spacing
import com.aldikitta.ui.util.isScrollingTowardsStart
import com.aldikitta.ui.util.items
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun LocalPresentableSection(
    state: LazyPagingItems<out MovieEntity>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    showRefreshLoading: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.default),
    scrollToBeginningItemsStart: Int = 30,
    onPresentableClick: (Int) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val isScrollingLeft = gridState.isScrollingTowardsStart()
    val showScrollToBeginningButton by derivedStateOf {
        val visibleMaxItem = gridState.firstVisibleItemIndex > scrollToBeginningItemsStart

        visibleMaxItem && isScrollingLeft
    }
    val onScrollToStartClick: () -> Unit = {
        coroutineScope.launch {
            gridState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .gridVerticalScrollBar(gridState),
            state = gridState,
            contentPadding = contentPadding,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(state) { presentable ->
                presentable?.let {
                    LocalPresentableItem(
                        presentableState = PresentableLocalItemState.Result(it),
                        onClick = { onPresentableClick(it.id) }
                    )
                }
            }
            state.apply {
                when {
                    loadState.refresh is LoadState.Loading && showRefreshLoading -> {
                        items(12) {
                            LocalPresentableItem(
                                presentableState = PresentableLocalItemState.Loading
                            )
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        items(3) {
                            LocalPresentableItem(
                                presentableState = PresentableLocalItemState.Loading
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        items(count = 3) {
                            LocalPresentableItem(
                                presentableState = PresentableLocalItemState.Error
                            )
                        }
                    }
                    loadState.refresh is LoadState.Error ->{
                        //call something
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = MaterialTheme.spacing.small, top = MaterialTheme.spacing.small),
            visible = showScrollToBeginningButton,
            enter = slideIn(
                animationSpec = tween(),
                initialOffset = { size -> IntOffset(size.width, 0) }),
            exit = fadeOut(animationSpec = spring()) + scaleOut(
                animationSpec = spring(),
                targetScale = 0.3f
            )
        ) {
            MovplayScrollToTopButton(
                onClick = onScrollToStartClick
            )
        }
    }
}