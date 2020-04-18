package ru.yweber.flaskdionysus.ui.app

import ru.terrakok.cicerone.NavigatorHolder
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.app.state.AppState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */


class AppViewModel @Inject constructor(
    private val router: GlobalRouter,
    navigatorHolder: NavigatorHolder
) :
    BaseViewModel<AppState>(navigatorHolder) {

    override val defaultState: AppState
        get() = AppState()

    fun appColdStart() {
        router.newRootScreen(Screens.MainFlowScreen)
    }


}