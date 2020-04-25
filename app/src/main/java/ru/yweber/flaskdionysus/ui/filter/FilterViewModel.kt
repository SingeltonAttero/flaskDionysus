package ru.yweber.flaskdionysus.ui.filter

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.Chips
import ru.yweber.flaskdionysus.core.adapter.state.FilterItemState
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.model.entity.FilterEntity
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.model.interactor.FilterUseCase
import ru.yweber.flaskdionysus.system.ResourceManager
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.filter.state.FilterState
import toothpick.InjectConstructor

/**
 * Created on 22.04.2020
 * @author YWeber */

@InjectConstructor
class FilterViewModel(
    private val resourceManager: ResourceManager,
    private val filterUseCase: FilterUseCase,
    private val globalRouter: GlobalRouter
) : BaseViewModel<FilterState>() {

    override val defaultState: FilterState
        get() = FilterState(listOf())

    init {
        launch {
            filterUseCase.startLoadFilter()
                .collect {
                    createBaseCardHolder()
                }
        }
        launch {
            filterUseCase.getSelectComponent()
                .flatMapLatest { select ->
                    filterUseCase.getFilter().map { select to it }
                }
                .collect { pair ->
                    pair.first.forEach { (type, ids) ->
                        val list = when (type) {
                            ItemTypeFilter.CONTAINS -> {
                                findEntity(FilterEntity.Type.INGREDIENT, ids, pair)
                            }
                            ItemTypeFilter.NOT_CONTAINS -> {
                                findEntity(FilterEntity.Type.INGREDIENT, ids, pair)
                            }
                            ItemTypeFilter.FORTRESS -> {
                                findEntity(FilterEntity.Type.FORTRESS_LEVELS, ids, pair)
                            }
                            ItemTypeFilter.VOLUMES -> {
                                findEntity(FilterEntity.Type.VOLUMES, ids, pair)
                            }
                            ItemTypeFilter.COMPLICATION -> {
                                findEntity(FilterEntity.Type.COMPLICATION_LEVELS, ids, pair)
                            }
                            ItemTypeFilter.OTHER -> {
                                findEntity(FilterEntity.Type.OTHER, ids, pair)
                            }
                        }
                        val modificationCardState = currentState.filters
                            .map { cardState ->
                                if (cardState.type == type) {
                                    cardState.copy(chips = list.map {
                                        Chips(it.id, it.name, it.type)
                                    }.distinct())
                                } else cardState
                            }
                        action.value = currentState.copy(modificationCardState)
                    }
                }
        }
    }

    private fun findEntity(
        typeEntity: FilterEntity.Type,
        ids: List<Int>,
        pair: Pair<Map<ItemTypeFilter, List<Int>>, List<FilterEntity>>
    ): List<FilterEntity> {
        return ids.map {
            val filter = pair.second
                .filter {
                    it.type == typeEntity
                }.filter { ids.contains(it.id) }
            filter
        }.flatten()
    }

    private fun createBaseCardHolder() {
        action.value = currentState.copy(
            filters = listOf(
                FilterItemState(
                    title = resourceManager.getString(R.string.contains),
                    chips = listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add_ingredient),
                    type = ItemTypeFilter.CONTAINS
                ),
                FilterItemState(
                    title = resourceManager.getString(R.string.not_contains),
                    chips = listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add_ingredient),
                    type = ItemTypeFilter.NOT_CONTAINS
                ),
                FilterItemState(
                    title = resourceManager.getString(R.string.fortress),
                    chips = listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add_fortess),
                    type = ItemTypeFilter.FORTRESS
                ),
                FilterItemState(
                    title = resourceManager.getString(R.string.volumes_drink),
                    chips = listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add),
                    type = ItemTypeFilter.VOLUMES
                ), FilterItemState(
                    title = resourceManager.getString(R.string.complication_drink),
                    chips = listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add),
                    type = ItemTypeFilter.COMPLICATION
                ), FilterItemState(
                    resourceManager.getString(R.string.another),
                    listOf(),
                    nameFilterButton = resourceManager.getString(R.string.add),
                    type = ItemTypeFilter.OTHER
                )
            )
        )
    }

    fun removeChip(name: String, position: Int) {
        val currentFilter = currentState.filters.getOrNull(position) ?: return
        val newChips = currentFilter.chips.filter { it.title != name }
        launch {
            filterUseCase.setSelect(currentFilter.type, newChips.map { it.id })
        }
    }

    fun openFilterDialog(type: ItemTypeFilter) {
        globalRouter.show(Screens.ChooserDialogHolder(type))
    }

    fun backTo() {
        globalRouter.backTo(Screens.HomeListDrinkScreen)
    }

}