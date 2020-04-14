package ru.yweber.flaskdionysus.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.page.DrinksPageDataSource
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.di.DrinkDayHolder
import ru.yweber.flaskdionysus.di.DrinkDayRouter
import ru.yweber.flaskdionysus.model.interactor.ListDrinkUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.home.state.ListDrinkState
import toothpick.InjectConstructor

/**
 * Created on 31.03.2020
 * @author YWeber */
private const val PAGE_MAX_ITEM = 50

@InjectConstructor
class HomeListDrinkViewModel(
    private val useCase: ListDrinkUseCase,
    @DrinkDayRouter private val drinkRouter: Router,
    @DrinkDayHolder private val navigatorHolder: NavigatorHolder
) :
    BaseViewModel<ListDrinkState>(navigatorHolder) {

    private val pageList: PagedList<DrinkCardItem>
        get() = PagedList
            .Builder(DrinksPageDataSource(useCase, viewModelScope), config())
            .setFetchExecutor {
                CoroutineScope(Dispatchers.IO).launch {
                    it.run()
                }
            }.setNotifyExecutor {
                launch {
                    it.run()
                }
            }
            .build()

    override val defaultState: ListDrinkState
        get() = ListDrinkState(pageList)

    init {
        action.value = currentState
    }


    private fun config(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(PAGE_MAX_ITEM)
            .setEnablePlaceholders(false)
            .build()
    }

    fun navigateDrinkDay() {
        drinkRouter.newRootScreen(Screens.DrinkTheDayFlowScreen)
    }

}