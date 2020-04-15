package ru.yweber.flaskdionysus.core.adapter.page

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem
import ru.yweber.flaskdionysus.model.entity.DrinkEntity
import ru.yweber.flaskdionysus.model.entity.DrinksEntity
import ru.yweber.flaskdionysus.model.interactor.ListDrinkUseCase
import timber.log.Timber

/**
 * Created on 15.04.2020
 * @author YWeber */

class DrinksPageDataSource(
    private val useCase: ListDrinkUseCase,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, DrinkCardItem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DrinkCardItem>) {
        scope.launch {
            val currentPage = 0
            val nextPage = currentPage + 1
            useCase.pageDrinks(currentPage).collect {
                if (it is DrinksEntity.Result) {
                    val createDrinkItem = createDrinkItem(it.list)
                    callback.onResult(createDrinkItem, null, nextPage)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DrinkCardItem>) {
        scope.launch {
            val currentPage = params.key
            val nextPage = currentPage + 1
            useCase.pageDrinks(currentPage).collect {
                if (it is DrinksEntity.Result) {
                    callback.onResult(createDrinkItem(it.list), nextPage)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DrinkCardItem>) {}

    private fun createDrinkItem(drinks: List<DrinkEntity>): List<DrinkCardItem> {
        Timber.e(" TEst testtest${drinks.map { it.id }}")
        return drinks.map {
            DrinkCardItem(
                it.id,
                it.icon, it.drinkName,
                it.properties, it.ingredients,
                it.drinkRating, it.flacky,
                it.fire, it.iba
            )
        }
    }

    override fun invalidate() {
        scope.cancel()
        super.invalidate()
    }
}