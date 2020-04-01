package ru.yweber.flaskdionysus.ui.start

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.di.module.MainFlowHolder
import ru.yweber.flaskdionysus.di.module.MainFlowRouter
import ru.yweber.flaskdionysus.model.TestInteractor
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.start.state.StartState
import timber.log.Timber
import javax.inject.Inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowViewModel @Inject constructor(
    private val interactor: TestInteractor,
    @MainFlowRouter private val router: Router,
    @MainFlowHolder navigatorHolder: NavigatorHolder
) : BaseViewModel<StartState>(navigatorHolder) {

    override val defaultState: StartState
        get() = StartState()

    fun navigate() {
        Timber.e(interactor.test)
        router.newRootScreen(Screens.HomeListDrinkScreen)
    }
}