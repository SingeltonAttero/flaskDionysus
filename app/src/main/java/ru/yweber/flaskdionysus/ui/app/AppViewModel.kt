package ru.yweber.flaskdionysus.ui.app

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.terrakok.cicerone.NavigatorHolder
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.core.notifier.RetryErrorNotifier
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.app.state.AppState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */


class AppViewModel @Inject constructor(
    private val router: GlobalRouter,
    private val errorNotifier: RetryErrorNotifier,
    navigatorHolder: NavigatorHolder
) :
    BaseViewModel<AppState>(navigatorHolder) {

    override val defaultState: AppState
        get() = AppState(isError = false)

    init {
        errorNotifier
            .eventError
            .onEach { action.value = currentState.copy(isError = it) }
            .launchIn(viewModelScope)
    }

    fun retryError() {
        launch {
            errorNotifier.retryRequest()
        }
    }

    fun appColdStart() {
        router.newRootScreen(Screens.MainFlowScreen)
    }


}