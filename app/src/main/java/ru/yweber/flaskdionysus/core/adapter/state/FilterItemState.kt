package ru.yweber.flaskdionysus.core.adapter.state

import ru.yweber.flaskdionysus.model.entity.FilterEntity
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter

/**
 * Created on 22.04.2020
 * @author YWeber */

data class FilterItemState(
    val title: String,
    val chips: List<Chips>,
    val nameFilterButton: String,
    val type: ItemTypeFilter
)

data class Chips(
    val id: Int,
    val title: String,
    val type: FilterEntity.Type
)