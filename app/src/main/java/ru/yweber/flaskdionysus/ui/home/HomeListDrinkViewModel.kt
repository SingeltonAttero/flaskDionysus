package ru.yweber.flaskdionysus.ui.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.di.DrinkDayHolder
import ru.yweber.flaskdionysus.di.DrinkDayRouter
import ru.yweber.flaskdionysus.model.entity.DrinkEntity
import ru.yweber.flaskdionysus.model.entity.DrinksEntity
import ru.yweber.flaskdionysus.model.interactor.ListDrinkUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.InjectConstructor

/**
 * Created on 31.03.2020
 * @author YWeber */

@InjectConstructor
class HomeListDrinkViewModel(
    private val useCase: ListDrinkUseCase,
    @DrinkDayRouter private val drinkRouter: Router,
    @DrinkDayHolder private val navigatorHolder: NavigatorHolder
) :
    BaseViewModel<ListDrinkState>(navigatorHolder) {

    override val defaultState: ListDrinkState
        get() = ListDrinkState(listOf())

    private var startPage: Int = 0

    init {
        launch {
            useCase.pageDrinks(startPage)
                .onStart { /* TODO handle loading */ }
                .onEach { action.value = reduceState(it) }
                .launchIn(viewModelScope)
        }
    }

    private fun reduceState(it: DrinksEntity): ListDrinkState {
        return when (it) {
            is DrinksEntity.Error -> {
                currentState
            }
            is DrinksEntity.Result -> {
                currentState.copy(listDrink = createDrinkItem(it.list))
            }
        }
    }

    private fun createDrinkItem(drinks: List<DrinkEntity>): List<DrinkCardItem> {
        return drinks.map {
            DrinkCardItem(
                it.icon, it.drinkName,
                it.properties, it.ingredients,
                it.drinkRating, it.flacky,
                it.fire, it.iba
            )
        }
    }


    fun navigateDrinkDay() {
        drinkRouter.newRootScreen(Screens.DrinkTheDayFlowScreen)
    }

}