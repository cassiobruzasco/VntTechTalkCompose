package com.cassiobruzasco.vntcomposetechtalk.ui.screen.first

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cassiobruzasco.vntcomposetechtalk.R
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.component.NavigationButton
import com.cassiobruzasco.vntcomposetechtalk.ui.theme.Purple700

@Composable
fun FirstScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: FirstViewModel = hiltViewModel()

        /**
         * Here we can see that both StateFlow and MutableState work well and we can
         * accomplish what we wanted with both, but with StateFlow we can take advantage
         * of flow's operators such as zip, combine, map, filter, etc
         * also if we need to use savedStateHandle to preserve a state in case of
         * process death it's more straight forward with StateFlow
         * and for last it's more reusable because StateFlow is Compose free, which means
         * it doesn't need to be in compose to work
         * For simple use consider using MutableState and for more complex operations use
         * Flow and StateFlow
         */
        val composeRoll = viewModel.composeRoll
        val flowRoll by viewModel.roll.collectAsStateWithLifecycle()

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Dice(roll = composeRoll)
            Dice(roll = flowRoll)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = viewModel::rollInUi,
            ) {
                Text(
                    text = "Roll",
                    fontSize = 25.sp
                )
            }
            NavigationButton(
                navController = navController,
                screen = "second_screen/$flowRoll",
                text = "Go to second screen"
            )
        }
    }
}

@Composable
fun Dice(
    modifier: Modifier = Modifier,
    roll: Int
) {
    val diceImage = when (roll) {
        1 -> R.drawable.icn_one
        2 -> R.drawable.icn_two
        3 -> R.drawable.icn_three
        4 -> R.drawable.icn_four
        5 -> R.drawable.icn_five
        6 -> R.drawable.icn_six
        else -> R.drawable.icn_none
    }

    Image(
        painter = painterResource(id = diceImage),
        contentDescription = "Dice",
        modifier = modifier.fillMaxWidth(),
        alignment = Alignment.Center,
        colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(Purple700) else null
    )
}