package ru.yweber.flaskdionysus.ui.main

import kotlinx.coroutines.delay
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.di.MainFlowHolder
import ru.yweber.flaskdionysus.di.MainFlowRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.main.state.MainState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowViewModel @Inject constructor(
    private val router: GlobalRouter,
    @MainFlowRouter private val mainRouter: Router,
    @MainFlowHolder navigatorHolder: NavigatorHolder
) : BaseViewModel<MainState>(navigatorHolder) {

    override val defaultState: MainState
        get() = MainState()


    fun navigateToAboutProject() {
        router.show(Screens.LoadingDialogHolder)
        launch {
            delay(2000)
            router.dismiss(Screens.LoadingDialogHolder)
        }
    }

    fun navigateToDrinks() {
        mainRouter.newRootScreen(Screens.HomeListDrinkScreen)
    }
}