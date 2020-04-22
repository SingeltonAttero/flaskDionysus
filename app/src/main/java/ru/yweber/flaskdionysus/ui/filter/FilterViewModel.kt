package ru.yweber.flaskdionysus.ui.filter

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.Chips
import ru.yweber.flaskdionysus.core.adapter.state.FilterItemState
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.filter.state.FilterState
import toothpick.InjectConstructor

/**
 * Created on 22.04.2020
 * @author YWeber */

@InjectConstructor
class FilterViewModel(private val globalRouter: GlobalRouter) : BaseViewModel<FilterState>() {

    override val defaultState: FilterState
        get() = FilterState(listOf())

    init {
        action.value = currentState.copy(
            filters = listOf(
                FilterItemState(
                    "Содержит",
                    listOf(Chips(1, "Текила"), Chips(2, "Апельсиновый сок"), Chips(3, "сок"), Chips(4, "вишня")),
                    "Еще ингредиент"
                ),
                FilterItemState(
                    "Не содержит",
                    listOf(Chips(11, "Апельсиновый сок"), Chips(34, "сок"), Chips(35, "вишня")),
                    "Еще ингредиент"
                ),
                FilterItemState("Крепость", listOf(Chips(342, "Текила"), Chips(345, "вишня")), "Еще крепость"),
                FilterItemState(
                    "Крепость 2",
                    listOf(Chips(305, "Апельсиновый сок"), Chips(5853, "сок"), Chips(4305, "вишня")),
                    "Еще"
                ),
                FilterItemState("Крепость 3", listOf(), "Еще")
            )
        )

    }

    fun removeChip(name: String, position: Int) {
        val currentFilter = currentState.filters.getOrNull(position) ?: return
        val newChips = currentFilter.chips.filter { it.title != name }
        val newFilters = currentState.filters.map {
            if (it == currentFilter) {
                it.copy(chips = newChips)
            } else it
        }
        action.value = currentState.copy(filters = newFilters)
    }

    fun openFilterDialog(type: String) {
        globalRouter.show(Screens.ChooserDialogHolder)
    }

    fun backTo() {
        globalRouter.backTo(Screens.HomeListDrinkScreen)
    }

}