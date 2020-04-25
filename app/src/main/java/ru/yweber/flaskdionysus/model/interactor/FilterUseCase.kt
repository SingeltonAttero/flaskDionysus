package ru.yweber.flaskdionysus.model.interactor

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.model.repository.FilterRepository
import javax.inject.Inject

/**
 * Created on 22.04.2020
 * @author YWeber */

class FilterUseCase @Inject constructor(private val repository: FilterRepository) {
    fun getFilter() = repository.cacheFilterEvent

    fun startLoadFilter() = repository.startLoadFilters()

    fun searchComponent(name: String) = repository.cacheFilterEvent
        .map {
            it.filter { filters ->
                filters.name.contains(name, true)
            }
        }.debounce(600)

    fun getSelectComponent() = repository.selectComponentEvent

    suspend fun setSelect(type: ItemTypeFilter, ids: List<Int>) {
        repository.setSelectComponent(type, ids)
    }
}