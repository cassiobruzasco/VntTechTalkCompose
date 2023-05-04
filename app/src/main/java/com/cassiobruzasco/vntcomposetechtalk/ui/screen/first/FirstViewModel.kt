package com.cassiobruzasco.vntcomposetechtalk.ui.screen.first

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

@ViewModelScoped
class FirstViewModel: ViewModel() {

    private companion object {
        const val TAG = "FirstViewModel"
    }

    init {
        collectOnEachFlow()
    }

    private val _roll = MutableStateFlow(0)
    val roll = _roll.asStateFlow()

    var composeRoll by mutableStateOf(0)
        private set

    fun rollInUi() {
        _roll.value = Random.nextInt(1, 7)
        //or _roll.update { Random.nextInt(1, 7) }
        // Difference between .value and .update
        // .update is atomic and is good to use inside a coroutine
        // update atomically change their reference to the object
        // value change the current value of the state directly at the object instance
        composeRoll = Random.nextInt(1, 7)
    }

    /**
     * collect() and collectLatest() comparison
     */
    private fun collectOnEachFlow() {
        val flow = flow {
            delay(150L)
            emit("ENTRADA")
            delay(100L)
            emit("PRATO PRINCIPAL")
            delay(100L)
            emit("SOBREMESA")
        }
        viewModelScope.launch {
            flow.onEach {
                Log.d(TAG, "### $it ENTREGUE")
            }
                // if you want to run this collect in a different coroutine, add a .buffer
                .collectLatest { // change it to .collect to see the right scenario, where we collect every emit
                    Log.d(TAG, "### CLIENTE COMENDO $it")
                    delay(200L)
                    Log.d(TAG, "### CLIENTE TERMINOU DE COMER $it")
                }
        }
    }
}