package ru.yweber.flaskdionysus.ui.drinkday.detailed

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.drinkday.detailed.state.DrinkDayDetailedState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayDetailedViewModel : BaseViewModel<DrinkDayDetailedState>() {
    override val defaultState: DrinkDayDetailedState
        get() = DrinkDayDetailedState()
}