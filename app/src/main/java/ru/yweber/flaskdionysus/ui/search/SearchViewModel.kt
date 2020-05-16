package ru.yweber.flaskdionysus.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.search.SearchDataSource
import ru.yweber.flaskdionysus.core.adapter.state.DrinkSearchItemState
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.core.notifier.RetryErrorNotifier
import ru.yweber.flaskdionysus.model.interactor.SearchUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.search.state.SearchState
import toothpick.InjectConstructor

/**
 * Created on 14.05.2020
 * @author YWeber */

@InjectConstructor
class SearchViewModel(
    private val globalRouter: GlobalRouter,
    private val searchUseCase: SearchUseCase,
    private val retryError: RetryErrorNotifier
) : BaseViewModel<SearchState>() {

    override val defaultState: SearchState
        get() = SearchState(true, null, true)


    init {
        action.value = currentState
    }

    fun backTo() {
        globalRouter.exit()
    }

    fun clickComponent(item: DrinkSearchItemState) {
        globalRouter.navigateTo(Screens.DrinkDetailedScreen(item.id))
    }

    fun search(search: String?) {
        if (search.isNullOrEmpty()) {
            action.value = currentState.copy(searchItems = null, emptySearch = true)
        } else {
            action.value = currentState.copy(searchItems = newSearchList(search), emptySearch = false)
        }
    }


    private fun newSearchList(search: String): PagedList<DrinkSearchItemState> {
        return PagedList
            .Builder(SearchDataSource(searchUseCase, viewModelScope, retryError, search) {
                action.value = currentState.copy(searchItems = null, emptySearch = true)
            }, config())
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
    }

    private fun config(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(50)
            .setEnablePlaceholders(false)
            .build()
    }
}