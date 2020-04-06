package ru.yweber.flaskdionysus.ui.home.drinkday

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.home.drinkday.state.DrinkTheDayState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkTheDayViewModel : BaseViewModel<DrinkTheDayState>() {
    override val defaultState: DrinkTheDayState
        get() = DrinkTheDayState()
}