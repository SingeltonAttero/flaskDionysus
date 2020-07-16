package ru.yweber.flaskdionysus.core.notifier

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

/**
 * Created on 16.04.2020
 * @author YWeber */

class VisibleToolbarNotifier @Inject constructor() {
    private val notifier = ConflatedBroadcastChannel<Boolean>()

    val event: Flow<Boolean>
        get() = notifier.asFlow()

    fun visibleToolbar(visible: Boolean) {
        notifier.offer(visible)
    }
}