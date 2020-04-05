package ru.yweber.flaskdionysus.ui.app

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.app.state.AppState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */


class AppViewModel @Inject constructor(
    private val router: Router,
    navigatorHolder: NavigatorHolder
) :
    BaseViewModel<AppState>(navigatorHolder) {

    override val defaultState: AppState
        get() = AppState()


    fun navigateStart() {
        router.newRootScreen(Screens.StartFlowScreen)
        action.value = currentState.copy(isStartMainScreen = true)
    }


}