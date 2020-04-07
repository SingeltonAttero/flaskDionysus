package ru.yweber.flaskdionysus.ui.drinkday.tools

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.drinkday.tools.state.ToolsDrinkState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class ToolsDrinkViewModel : BaseViewModel<ToolsDrinkState>() {
    override val defaultState: ToolsDrinkState
        get() = ToolsDrinkState()
}