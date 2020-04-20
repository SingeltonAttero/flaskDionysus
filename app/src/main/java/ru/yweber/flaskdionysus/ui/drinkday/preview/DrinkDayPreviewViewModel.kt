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
        action.value = currentState.copy(
            imagePath = "https://kak-nazyvaetsya.ru/wp-content/uploads/2019/05/b52.jpg",
            drinkName = "Текила Санрайз",
            rating = 4,
            checks = "Попробовали: 132 человека",
            levelCooking = "Сложность: средне",
            alcoholStrength = "Крепость: легкий"
        )
    }
}