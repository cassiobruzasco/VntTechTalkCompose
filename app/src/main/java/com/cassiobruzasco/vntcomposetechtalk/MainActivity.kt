package com.cassiobruzasco.vntcomposetechtalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cassiobruzasco.vntcomposetechtalk.ui.theme.VntComposeTechTalkTheme
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.first.FirstScreen
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.second.SecondScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VntComposeTechTalkTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "first_screen"
                ) {
                    composable("first_screen") {
                        FirstScreen(navController = navController)
                    }

                    composable(
                        route = "second_screen/{roll}",
                        arguments = listOf(navArgument("roll") { type = NavType.IntType })
                    ) {
                        val roll = remember {
                            it.arguments?.getInt("roll") ?: 0
                        }
                        SecondScreen(navController = navController, roll = roll)
                    }
                }
            }
        }
    }
}