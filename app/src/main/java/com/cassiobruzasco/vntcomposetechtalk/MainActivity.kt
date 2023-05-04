package com.cassiobruzasco.vntcomposetechtalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.first.FirstScreen
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.second.SecondScreen
import com.cassiobruzasco.vntcomposetechtalk.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                val navController = rememberNavController()

                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "first_screen"
                    ) {
                        composable("first_screen") {
                            FirstScreen(navController = navController)
                        }

                        composable(
                            route = "second_screen",
                        ) {
                            SecondScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}