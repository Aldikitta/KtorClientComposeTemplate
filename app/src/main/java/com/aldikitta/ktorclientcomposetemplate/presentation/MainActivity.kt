package com.aldikitta.ktorclientcomposetemplate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.aldikitta.ktorclientcomposetemplate.KtorApp
import com.aldikitta.ktorclientcomposetemplate.navigation.Navigation
import com.aldikitta.ktorclientcomposetemplate.ui.KtorAppMain
import com.aldikitta.ui.theme.KtorClientComposeTemplateTheme
import com.aldikitta.util.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KtorClientComposeTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KtorAppMain(
                        networkMonitor = networkMonitor, windowSizeClass = calculateWindowSizeClass(
                            activity = this
                        )
                    )
                }
            }
        }
    }
}