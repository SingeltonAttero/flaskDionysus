package ru.yweber.flaskdionysus.ui.home

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.di.DrinkDayHolder
import ru.yweber.flaskdionysus.di.DrinkDayRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.InjectConstructor

/**
 * Created on 31.03.2020
 * @author YWeber */

@InjectConstructor
class HomeListDrinkViewModel(
    @DrinkDayRouter private val drinkRouter: Router,
    @DrinkDayHolder private val navigatorHolder: NavigatorHolder
) :
    BaseViewModel<ListDrinkState>(navigatorHolder) {

    override val defaultState: ListDrinkState
        get() = ListDrinkState(listOf())

    init {
        val toList = (0..100).map {
            DrinkCardItem(
                R.drawable.ic_drink,
                "Текила Санрайз",
                "Сложность: средне",
                "Крепость: легкий ",
                "Ром, ананасовый сок, \n" +
                        "кокосовый сироп и еще кое-чтококосовый сироп и еще кое-чтококосовый сироп и еще кое-чтококосовый сироп и еще кое-чтококосовый сироп и еще кое-что"
            )
        }.toList()
        val toMutableList = currentState.listDrink.toMutableList()
        toMutableList.addAll(toList)
        action.value = currentState.copy(listDrink = toMutableList)
    }

    fun navigateDrinkDay() {
        drinkRouter.newRootScreen(Screens.DrinkTheDayFlowScreen)
    }

}