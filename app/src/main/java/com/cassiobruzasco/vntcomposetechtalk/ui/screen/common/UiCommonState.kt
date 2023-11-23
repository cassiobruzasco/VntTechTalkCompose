package com.cassiobruzasco.vntcomposetechtalk.ui.screen.common

import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.ResponseData

sealed class UiCommonState<out T> {
    data object Idle : UiCommonState<Nothing>()
    data object Loading : UiCommonState<Nothing>()
    data class Success<T>(val data: T) : UiCommonState<T>()
    data class Error(val errorMsg: String) : UiCommonState<Nothing>()
}

fun <T : Any, U : Any> ResponseData<T>.toUiCommonState(predicate: (ResponseData.Success<T>) -> U) =
    when (this) {
        is ResponseData.Success -> UiCommonState.Success(predicate(this))
        is ResponseData.Error -> UiCommonState.Error(this.errorMsg)
        is ResponseData.Exception -> UiCommonState.Error(this.e.message ?: "")
    }