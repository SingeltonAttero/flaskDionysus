package ru.yweber.flaskdionysus.ui.home.state

import androidx.paging.PagedList
import ru.yweber.flaskdionysus.core.adapter.state.DrinkCardItem

/**
 * Created on 31.03.2020
 * @author YWeber */

data class ListDrinkState(
    val listDrink: PagedList<DrinkCardItem>,
    val isLoad: Boolean = false
)