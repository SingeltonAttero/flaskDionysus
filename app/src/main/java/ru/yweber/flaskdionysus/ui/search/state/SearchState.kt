package ru.yweber.flaskdionysus.ui.search.state

import androidx.paging.PagedList
import ru.yweber.flaskdionysus.core.adapter.state.DrinkSearchItemState

/**
 * Created on 14.05.2020
 * @author YWeber */

data class SearchState(
    val init: Boolean,
    val searchItems: PagedList<DrinkSearchItemState>?,
    val emptySearch: Boolean
)