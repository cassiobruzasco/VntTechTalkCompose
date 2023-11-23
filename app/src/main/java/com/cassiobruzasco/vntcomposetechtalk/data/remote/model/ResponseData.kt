package com.cassiobruzasco.vntcomposetechtalk.data.remote.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

sealed class ResponseData<out T : Any> {
    data class Success<out T:  Any>(val response: T) : ResponseData<T>()
    data class Error(val errorMsg: String) : ResponseData<Nothing>()
    data class Exception(val e: Throwable) : ResponseData<Nothing>()
}

suspend fun <T : Any> handleApiResponse(
    execute: suspend () -> Response<T>
): Flow<ResponseData<T>> = flow {
    try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            emit(ResponseData.Success(body))
        } else {
            val error = response.errorBody()?.string() ?: "Unknown"
            emit(ResponseData.Error(error))
        }
    } catch (e: HttpException) {
        emit(ResponseData.Error(e.message()))
    } catch (e: Throwable) {
        emit(ResponseData.Exception(e))
    }
}