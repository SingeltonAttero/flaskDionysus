package ru.yweber.flaskdionysus.ui.drinkday.preview

import kotlinx.coroutines.flow.collect
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.model.interactor.DrinkDayUseCase
import ru.yweber.flaskdionysus.ui.drinkday.preview.state.DrinkDayPreviewState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayPreviewViewModel(private val useCase: DrinkDayUseCase) : BaseViewModel<DrinkDayPreviewState>() {
    override val defaultState: DrinkDayPreviewState
        get() = DrinkDayPreviewState(
            "Коктейль дня",
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
                        imagePath = createPreviewPath(it.previewIconPath),
                        drinkName = it.nameDrink,
                        rating = it.preview.rating,
                        checks = it.preview.tried,
                        levelCooking = it.preview.complication,
                        alcoholStrength = it.preview.fortress
                    )
                }

        }
    }

    private fun createPreviewPath(previewPath: String): String {
        return if (previewPath.isEmpty()) {
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Manhattan_Cocktail2.jpg/500px-Manhattan_Cocktail2.jpg"
        } else previewPath
    }

    fun endSharedAnimate() {
        action.value = currentState.copy(endShareAnimate = true)
    }
}