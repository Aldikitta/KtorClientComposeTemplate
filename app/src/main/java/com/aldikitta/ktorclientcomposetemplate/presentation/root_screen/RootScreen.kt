package com.aldikitta.ktorclientcomposetemplate.presentation.root_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.aldikitta.ktorclientcomposetemplate.R
import com.aldikitta.ktorclientcomposetemplate.navigation.Screen
import com.aldikitta.remote_request.navigation.navigateToRemoteGraph

@Composable
fun RootScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate(route = Screen.LocalScreen.route) {

                }

            }) {
                Text(text = stringResource(id = R.string.local_screen))
            }
            Button(onClick = {
                navController.navigateToRemoteGraph()
//                navController.navigate(route = Screen.RemoteScreen.route) {
//
//                }
            }) {
                Text(text = stringResource(id = R.string.remote_screen))
            }
        }
    }
}