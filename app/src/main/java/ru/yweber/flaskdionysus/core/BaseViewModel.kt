package ru.yweber.flaskdionysus.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

/**
 * Created on 31.03.2020
 * @author YWeber */

abstract class BaseViewModel<T>(private val navigatorHolder: NavigatorHolder? = null) : ViewModel() {

    protected abstract val defaultState: T

    protected val action: MutableLiveData<T> = MutableLiveData()

    protected val currentState: T
        get() = action.value ?: defaultState

    val state: LiveData<T>
        get() = action

    protected fun launch(start: suspend () -> Unit) = viewModelScope.launch { start.invoke() }

    fun addNavigator(navigator: Navigator) {
        navigatorHolder?.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder?.removeNavigator()
    }
}