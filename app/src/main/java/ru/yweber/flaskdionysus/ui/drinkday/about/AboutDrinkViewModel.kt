package ru.yweber.flaskdionysus.ui.drinkday.about

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.drinkday.about.state.AboutDrinkState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class AboutDrinkViewModel : BaseViewModel<AboutDrinkState>() {
    override val defaultState: AboutDrinkState
        get() = AboutDrinkState()
}