package com.cassiobruzasco.vntcomposetechtalk.ui.screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NavigationButton(
    navController: NavController,
    screen: String,
    text: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(20.dp)),
        onClick = { navController.navigate(screen) }
    ) {
        Text(
            text = text,
            fontSize = 25.sp
        )
    }
}