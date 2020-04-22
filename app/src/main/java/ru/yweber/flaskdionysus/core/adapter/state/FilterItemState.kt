package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 22.04.2020
 * @author YWeber */

data class FilterItemState(
    val title: String,
    val chips: List<Chips>,
    val nameFilterButton: String
)

data class Chips(
    val id: Int,
    val title: String
)