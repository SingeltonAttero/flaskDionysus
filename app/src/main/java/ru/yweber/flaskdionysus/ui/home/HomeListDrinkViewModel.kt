package ru.yweber.flaskdionysus.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.page.DrinksPageDataSource
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.core.notifier.FilterApplyNotifier
import ru.yweber.flaskdionysus.core.notifier.RetryErrorNotifier
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
    private val retryNotifier: RetryErrorNotifier,
    private val filterApplyNotifier: FilterApplyNotifier,
    private val globalRouter: GlobalRouter,
    @DrinkDayRouter private val drinkRouter: Router,
    @DrinkDayHolder private val navigatorHolder: NavigatorHolder
) : BaseViewModel<ListDrinkState>(navigatorHolder) {

    private val pageList: PagedList<DrinkCardItem>
        get() = PagedList
            .Builder(DrinksPageDataSource(useCase, viewModelScope, retryNotifier, ::loaded), config())
            .setFetchExecutor {
                launch {
                    CoroutineScope(Dispatchers.IO).launch {
                        it.run()
                    }
                }
            }.setNotifyExecutor {
                launch {
                    it.run()
                }
            }
            .build()

    override val defaultState: ListDrinkState
        get() = ListDrinkState(pageList, false)

    init {
        action.value = currentState.copy(isLoad = false)
        launch {
            retryNotifier.eventRetryRequest
                .collect {
                    action.value = currentState.copy(listDrink = pageList, isLoad = false)
                }
        }

        launch {
            filterApplyNotifier.applyFilterEvent
                .collect {
                    if (it) {
                        action.value?.listDrink?.detach()
                        action.value = currentState.copy(listDrink = pageList, isLoad = false)
                    }
                }
        }
    }

    private fun loaded(load: Boolean) {
        if (load) {
            launch {
                delay(300) // create recycler item or animate lag create
                action.value = currentState.copy(isLoad = true)
            }
        }
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

    fun expendMenu() {
        action.value = currentState.copy(animationFab = true, menuExpend = !currentState.menuExpend)
    }

    fun navigateSetting() {
        globalRouter.navigateTo(Screens.AboutProjectScreen)
    }

    fun navigateFilter() {
        action.value = currentState.copy(animationFab = false, menuExpend = !currentState.menuExpend)
        globalRouter.navigateTo(Screens.FilterScreen)
    }


}


