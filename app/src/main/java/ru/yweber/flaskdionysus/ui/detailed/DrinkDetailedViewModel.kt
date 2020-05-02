package ru.yweber.flaskdionysus.ui.detailed

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.detailed.state.DrinkDetailedState
import toothpick.InjectConstructor

/**
 * Created on 29.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDetailedViewModel(private val globalRouter: GlobalRouter) : BaseViewModel<DrinkDetailedState>() {

    override val defaultState: DrinkDetailedState
        get() = DrinkDetailedState()

    fun backTo() {
        globalRouter.backTo(Screens.HomeListDrinkScreen)
    }
}