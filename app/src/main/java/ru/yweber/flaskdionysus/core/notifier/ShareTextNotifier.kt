package ru.yweber.flaskdionysus.core.notifier

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

/**
 * Created on 10.05.2020
 * @author YWeber */
class ShareTextNotifier @Inject constructor() {

    private val action: MutableStateFlow<String?> = MutableStateFlow(null)

    val intentStartShare
        get() = action.filterIsInstance<String>()

    fun shareDrink(message: String) {
        restartShare()
        action.value = message
    }

    private fun restartShare() {
        action.value = null
    }

}