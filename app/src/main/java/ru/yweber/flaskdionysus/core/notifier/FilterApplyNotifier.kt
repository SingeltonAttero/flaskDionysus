package ru.yweber.flaskdionysus.core.notifier

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

/**
 * Created on 26.04.2020
 * @author YWeber */

class FilterApplyNotifier @Inject constructor() {

    private val action = ConflatedBroadcastChannel(false)

    val applyFilterEvent
        get() = action.asFlow()

    fun applyFilter() {
        action.offer(true)
    }
}