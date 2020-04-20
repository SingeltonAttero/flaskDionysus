package ru.yweber.flaskdionysus.ui.drinkday

import kotlinx.coroutines.flow.collect
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.di.DrinkDayNestedHolder
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.model.interactor.DrinkDayUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.drinkday.state.DrinkTheDayState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkTheDayFlowViewModel(
    private val useCase: DrinkDayUseCase,
    @DrinkDayNestedRouter private val nestedRouter: Router,
    @DrinkDayNestedHolder private val nestedNavigationHolder: NavigatorHolder
) : BaseViewModel<DrinkTheDayState>(nestedNavigationHolder) {

    override val defaultState: DrinkTheDayState
        get() = DrinkTheDayState(true, 0F)

    private var isPreview = false

    init {
        launch {
            useCase.startDrinkDay()
                .collect()
        }

    }

    fun startPreview() {
        nestedRouter.newRootScreen(Screens.DrinkDayPreviewScreen)
    }

    fun fabAnimationHeight(height: Float) {
        action.value = currentState.copy(height = height)
    }

    fun swipePreviewToDetailed() {
        if (isPreview) {
            nestedRouter.backTo(null)
        } else {
            nestedRouter.navigateTo(Screens.DrinkDayDetailedScreen)
        }
        isPreview = !isPreview
        action.value = currentState.copy(isPreview = !isPreview)
    }
}