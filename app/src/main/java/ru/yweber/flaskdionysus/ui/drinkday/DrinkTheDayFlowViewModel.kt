package ru.yweber.flaskdionysus.ui.drinkday

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.drinkday.state.DrinkTheDayState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkTheDayFlowViewModel(
    @DrinkDayNestedRouter private val nestedRouter: Router,
    @DrinkDayNestedHolder private val nestedNavigationHolder: NavigatorHolder
) : BaseViewModel<DrinkTheDayState>(nestedNavigationHolder) {

    override val defaultState: DrinkTheDayState
        get() = DrinkTheDayState()

    private var isPreview = false

    fun startPreview() {
        nestedRouter.navigateTo(Screens.DrinkDayPreviewScreen)

    }

    fun swipePreviewToDetailed() {
        if (isPreview) {
            nestedRouter.backTo(Screens.DrinkDayPreviewScreen)
        } else {
            nestedRouter.navigateTo(Screens.DrinkDayDetailedScreen)
        }
        isPreview = !isPreview
    }
}