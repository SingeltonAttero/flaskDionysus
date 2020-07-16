package ru.yweber.flaskdionysus.ui.drinkday.preview

import kotlinx.coroutines.flow.collect
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.model.interactor.DrinkDayUseCase
import ru.yweber.flaskdionysus.system.ResourceManager
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.drinkday.preview.state.DrinkDayPreviewState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayPreviewViewModel(
    private val useCase: DrinkDayUseCase,
    private val resourceManager: ResourceManager,
    @DrinkDayNestedRouter private val nestedRouter: Router,
    private val globalRouter: GlobalRouter
) : BaseViewModel<DrinkDayPreviewState>() {
    override val defaultState: DrinkDayPreviewState
        get() = DrinkDayPreviewState(
            -1,
            resourceManager.getString(R.string.drink_of_the_day),
            "",
            "",
            0,
            "",
            "",
            ""
        )

    init {
        launch {
            useCase.getDrinkDay()
                .collect {
                    action.value = currentState.copy(
                        id = it.id,
                        imagePath = createPreviewPath(it.previewIconPath),
                        drinkName = it.nameDrink,
                        rating = it.preview.rating,
                        tried = resourceManager.getString(R.string.tried_prefix, it.preview.tried),
                        levelCooking = resourceManager.getString(
                            R.string.cooking_level_prefix,
                            it.preview.complication
                        ),
                        alcoholStrength = resourceManager.getString(
                            R.string.alcohol_strength_prefix,
                            it.preview.fortress
                        ),
                        isStatusHot = it.isHot,
                        isStatusIba = it.isIba,
                        isStatusPuff = it.isPuff
                    )
                }

        }
    }

    private fun createPreviewPath(previewPath: String): String {
        return previewPath
    }

    fun endSharedAnimate() {
        action.value = currentState.copy(endShareAnimate = true)
    }

    fun navigateToFullDetailed() {
        globalRouter.navigateTo(Screens.DrinkDetailedScreen(currentState.id))
    }

    fun navigateToDetailedHeader() {
        nestedRouter.navigateTo(Screens.DrinkDayDetailedScreen)
    }
}