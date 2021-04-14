package com.gilbertparreno.cartrack.core.extensions

import kotlinx.coroutines.*

fun <R> CoroutineScope.launch(
    work: suspend CoroutineScope.() -> R,
    onSuccess: (R) -> Unit,
    onFailure: (error: Throwable) -> Unit
) {
    launch(Dispatchers.IO) {
        runCatching {
            work()
        }.also { result ->
            launch(Dispatchers.Main) {
                result.onSuccess {
                    onSuccess(it)
                }.onFailure {
                    onFailure(it)
                }
            }
        }
    }
}

fun <T> CoroutineScope.launch(work: suspend CoroutineScope.() -> T) {
    launch(Dispatchers.IO) {
        work()
    }
}