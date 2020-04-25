package ru.yweber.flaskdionysus.ui.filter.chooser

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.FilterChooserItemState
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.model.entity.FilterEntity
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.model.interactor.FilterUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.filter.chooser.state.ChooserState
import timber.log.Timber
import toothpick.InjectConstructor

/**
 * Created on 22.04.2020
 * @author YWeber */

@InjectConstructor
class ChooserViewModel(
    private val type: ItemTypeFilter,
    private val useCase: FilterUseCase,
    private val globalRouter: GlobalRouter
) : BaseViewModel<ChooserState>() {
    override val defaultState: ChooserState
        get() = ChooserState(
            items = listOf(),
            showSearch = type == ItemTypeFilter.NOT_CONTAINS || type == ItemTypeFilter.CONTAINS
        )

    init {
        allComponent()
    }


    fun selectComponent(id: Int) {
        Timber.e(id.toString())
        action.value = currentState.copy(items = currentState.items.map {
            if (id == it.id) {
                it.copy(select = !it.select)
            } else {
                it
            }
        }, isInitWindows = false)
    }

    fun searchComponent(name: String?) {
        val searchName = name ?: ""
        if (searchName.isNotEmpty()) {
            launch {
                useCase.searchComponent(searchName)
                    .take(1)
                    .onCompletion { isSelectedOldItems() }
                    .collect { filters ->
                        val ingredient = convertToItemsState(filters)
                        action.value = currentState.copy(
                            items = ingredient,
                            isInitWindows = false,
                            searchEmpty = ingredient.isEmpty(),
                            search = searchName
                        )
                    }
            }
        } else {
            allComponent(false)
        }
    }

    private fun allComponent(isInit: Boolean = true) {
        useCase.getFilter()
            .take(1)
            .onEach { list ->
                val ingredient = convertToItemsState(list)
                action.value = currentState.copy(
                    items = ingredient,
                    isInitWindows = isInit,
                    searchEmpty = false
                )
            }
            .onCompletion {
                isSelectedOldItems()
            }
            .launchIn(viewModelScope)

    }

    private fun isSelectedOldItems() {
        useCase.getSelectComponent()
            .onEach { selectMap ->
                val selected = selectMap[type] ?: listOf()
                val selectedItem = currentState.items.map {
                    if (selected.contains(it.id)) {
                        it.copy(select = true)
                    } else it
                }
                action.value = currentState.copy(items = selectedItem, isInitWindows = false)
            }
            .launchIn(viewModelScope)
    }

    private fun convertToItemsState(filters: List<FilterEntity>): List<FilterChooserItemState> {
        return filters.filter {
            when (type) {
                ItemTypeFilter.CONTAINS -> {
                    it.type == FilterEntity.Type.INGREDIENT
                }
                ItemTypeFilter.NOT_CONTAINS -> {
                    it.type == FilterEntity.Type.INGREDIENT
                }
                ItemTypeFilter.FORTRESS -> {
                    it.type == FilterEntity.Type.FORTRESS_LEVELS
                }
                ItemTypeFilter.VOLUMES -> {
                    it.type == FilterEntity.Type.VOLUMES
                }
                ItemTypeFilter.COMPLICATION -> {
                    it.type == FilterEntity.Type.COMPLICATION_LEVELS
                }
                ItemTypeFilter.OTHER -> {
                    it.type == FilterEntity.Type.OTHER
                }
            }
        }.map {
            FilterChooserItemState(it.id, it.name)
        }
    }

    fun approveFilter() {
        launch {
            useCase.setSelect(
                type,
                currentState.items.filter { it.select }.map { it.id }
            )
            globalRouter.dismiss(Screens.ChooserDialogHolder(type))

        }
    }
}