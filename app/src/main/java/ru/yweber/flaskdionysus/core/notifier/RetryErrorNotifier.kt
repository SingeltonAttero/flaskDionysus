package ru.yweber.flaskdionysus.core.notifier

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn

/**
 * Created on 21.04.2020
 * @author YWeber */

class RetryErrorNotifier {

    private val actionError = BroadcastChannel<Boolean>(10)
    private val actionRetry = BroadcastChannel<Unit>(10)


    val eventRetryRequest
        get() = actionRetry.asFlow()
            .flowOn(Dispatchers.Default)

    val eventError
        get() = actionError.asFlow()
            .flowOn(Dispatchers.Default)

    suspend fun errorVisible(error: Boolean) {
        actionError.send(error)
    }

    suspend fun retryRequest() {
        actionRetry.send(Unit)
    }

}