package ru.yweber.flaskdionysus.model.interactor

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.take
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.model.repository.DrinkRepository
import ru.yweber.flaskdionysus.model.repository.FilterRepository
import javax.inject.Inject

/**
 * Created on 14.04.2020
 * @author YWeber */

class ListDrinkUseCase @Inject constructor(
    private val repository: DrinkRepository,
    private val filterRepository: FilterRepository
) {
    fun pageDrinks(page: Int) = filterRepository.selectComponentEvent
        .take(1)
        .flatMapLatest {
            val includes = it[ItemTypeFilter.CONTAINS] ?: listOf()
            val except = it[ItemTypeFilter.NOT_CONTAINS] ?: listOf()
            val fortress = it[ItemTypeFilter.FORTRESS] ?: listOf()
            val volumes = it[ItemTypeFilter.VOLUMES] ?: listOf()
            val another = it[ItemTypeFilter.OTHER] ?: listOf()
            val reallyAnother = listOf(
                another.contains(1),
                another.contains(2),
                another.contains(3)
            )
            repository.pageDrink(page, includes, except, fortress, volumes, reallyAnother)
        }
}