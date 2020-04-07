package ru.yweber.flaskdionysus.ui.drinkday.formula

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.drinkday.formula.state.FormulaDrinkState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class FormulaDrinkViewModel : BaseViewModel<FormulaDrinkState>() {
    override val defaultState: FormulaDrinkState
        get() = FormulaDrinkState()
}