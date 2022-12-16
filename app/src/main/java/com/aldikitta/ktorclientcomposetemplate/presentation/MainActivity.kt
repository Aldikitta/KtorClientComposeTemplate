package com.aldikitta.ktorclientcomposetemplate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aldikitta.ktorclientcomposetemplate.navigation.Navigation
import com.aldikitta.ktorclientcomposetemplate.ui.theme.KtorClientComposeTemplateTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                  KtorClientComposeTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
//                movies.value?.let {
//                    MovieList(
//                        movies = it.results
//                    )
//                }

//                movies.value.let {
//                    when (it) {
//                        is Resource.Failure -> {
//                            context.toast(it.exception.message!!)
//                        }
//                        Resource.Loading -> {
//                            FullScreenProgressbar()
//                        }
//                        is Resource.Success -> {
//                            MovieList(
//                                movies = it.result.results
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}