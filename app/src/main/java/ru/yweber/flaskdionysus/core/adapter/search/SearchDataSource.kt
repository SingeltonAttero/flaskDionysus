package ru.yweber.flaskdionysus.core.adapter.search

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.yweber.flaskdionysus.core.adapter.state.DrinkSearchItemState
import ru.yweber.flaskdionysus.core.notifier.RetryErrorNotifier
import ru.yweber.flaskdionysus.model.interactor.SearchUseCase

/**
 * Created on 16.05.2020
 * @author YWeber */

class SearchDataSource(
    private val searchUseCase: SearchUseCase,
    private val scope: CoroutineScope,
    private val retryErrorNotifier: RetryErrorNotifier,
    private val search: String,
    private val emptySearch: () -> Unit
) : PageKeyedDataSource<Int, DrinkSearchItemState>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DrinkSearchItemState>) {
        scope.launch {
            val currentPage = 0
            val nextPage = currentPage + 1
            startLoad(currentPage, callback, nextPage)
            retryErrorNotifier.eventRetryRequest
                .collect {
                    startLoad(currentPage, callback, nextPage)
                }
        }
    }

    private suspend fun startLoad(
        currentPage: Int,
        callback: LoadInitialCallback<Int, DrinkSearchItemState>,
        nextPage: Int
    ) {
        searchUseCase.searchComponent(search, currentPage)
            .catch { retryErrorNotifier.errorVisible(true) }
            .collect { components ->
                if (components.isEmpty()) {
                    emptySearch.invoke()
                }
                callback.onResult(components.map {
                    DrinkSearchItemState(it.id, it.name, "Коктейль")
                }, null, nextPage)
            }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DrinkSearchItemState>) {
        scope.launch {
            val currentPage = params.key
            val nextPage = currentPage + 1
            searchUseCase.searchComponent(search, currentPage)
                .catch { emit(listOf()) }
                .collect { components ->
                    callback.onResult(components.map {
                        DrinkSearchItemState(it.id, it.name, "Коктейль")
                    }, nextPage)
                }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DrinkSearchItemState>) {}

    override fun invalidate() {
        scope.cancel()
        super.invalidate()
    }
}