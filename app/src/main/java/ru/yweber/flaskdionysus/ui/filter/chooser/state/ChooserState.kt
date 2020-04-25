package ru.yweber.flaskdionysus.ui.filter.chooser.state

import ru.yweber.flaskdionysus.core.adapter.state.FilterChooserItemState

/**
 * Created on 22.04.2020
 * @author YWeber */

data class ChooserState(
    val items: List<FilterChooserItemState>,
    val showSearch: Boolean,
    val isInitWindows: Boolean = true,
    val searchEmpty: Boolean = false,
    val search: String = ""
)