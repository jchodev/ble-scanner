package com.jerry.blescanner.basemodule.presentation

/*
    common UI data state
 */
sealed class UiDataState<out T> where T : Any? {
    object Initial : UiDataState<Nothing>()
    object Loading : UiDataState<Nothing>()

    data class Success<T>(val data: T) : UiDataState<T>()
    data class Failure(val errorAny: Any) : UiDataState<Nothing>()
}