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
        get() = AboutDrinkState("")

    init {
        action.value = currentState.copy(
            aboutDescription = "Коктейль «Восходящее солнце» был изобретён в 30-40-е годы в гостинице Arizona Biltmore Hotel. Первоначально рецепт включал чёрносмородиновый ликёр (Creme de Cassis) и сок лайма[5].\n" +
                    "Своё название (англ. sunrise — «восход») коктейль получил за внешний вид. Плотные компоненты (чёрносмородиновый ликёр или гранатовый сироп), оседая на дно стакана сквозь смесь сока и текилы, создают градацию цветов, напоминающую рассвет[6]."
        )
    }
}