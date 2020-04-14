package ru.yweber.flaskdionysus.ui.main

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.di.MainFlowHolder
import ru.yweber.flaskdionysus.di.MainFlowRouter
import ru.yweber.flaskdionysus.model.TestInteractor
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.main.state.MainState
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowViewModel @Inject constructor(
    private val interactor: TestInteractor,
    @MainFlowRouter private val mainRouter: Router,
    @MainFlowHolder navigatorHolder: NavigatorHolder,
    private val router: Router
) : BaseViewModel<MainState>(navigatorHolder) {

    override val defaultState: MainState
        get() = MainState()

    fun navigateToAboutProject() {
        router.navigateTo(Screens.AboutProjectScreen)
    }

    fun navigateToDrinks() {
        mainRouter.newRootScreen(Screens.HomeListDrinkScreen)
    }
}