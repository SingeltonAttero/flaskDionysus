package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 23.04.2020
 * @author YWeber */

data class FilterChooserItemState(
    val id: Int,
    val name: String,
    val select: Boolean = false
)