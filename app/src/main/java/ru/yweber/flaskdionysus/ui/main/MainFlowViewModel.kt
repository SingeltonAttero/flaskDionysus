package ru.yweber.flaskdionysus.ui.main

import kotlinx.coroutines.flow.collect
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.core.notifier.VisibleToolbarNotifier
import ru.yweber.flaskdionysus.di.MainFlowHolder
import ru.yweber.flaskdionysus.di.MainFlowRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.main.state.MainState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowViewModel @Inject constructor(
    private val notifier: VisibleToolbarNotifier,
    private val router: GlobalRouter,
    @MainFlowRouter private val mainRouter: Router,
    @MainFlowHolder navigatorHolder: NavigatorHolder
) : BaseViewModel<MainState>(navigatorHolder) {

    override val defaultState: MainState
        get() = MainState(false)

    init {
        launch {
            notifier.event
                .collect {
                    action.value = currentState.copy(visibleToolbar = it)
                }
        }
    }


    fun navigateToAboutProject() {
        router.navigateTo(Screens.AboutProjectScreen)
    }

    fun navigateToDrinks() {
        mainRouter.newRootScreen(Screens.HomeListDrinkScreen)
    }
}