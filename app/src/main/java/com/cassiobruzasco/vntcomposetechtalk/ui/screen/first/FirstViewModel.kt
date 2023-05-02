package com.cassiobruzasco.vntcomposetechtalk.ui.screen.first

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.random.Random

@ViewModelScoped
class FirstViewModel: ViewModel() {

    private val _roll = MutableStateFlow(0)
    val roll = _roll.asStateFlow()

    var composeRoll by mutableStateOf(0)
        private set

    fun rollInUi() {
        _roll.update { Random.nextInt(1, 7) }
        composeRoll = Random.nextInt(1, 7)
    }
}