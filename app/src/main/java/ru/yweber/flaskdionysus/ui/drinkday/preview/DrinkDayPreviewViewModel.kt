package ru.yweber.flaskdionysus.ui.drinkday.preview

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.ui.drinkday.preview.state.DrinkDayPreviewState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayPreviewViewModel : BaseViewModel<DrinkDayPreviewState>() {
    override val defaultState: DrinkDayPreviewState
        get() = DrinkDayPreviewState()
}