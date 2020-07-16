package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 15.05.2020
 * @author YWeber */

sealed class SearchItemState(open val id: Int, val type: String)
data class DrinkSearchItemState(
    override val id: Int,
    val name: String,
    val typeDescription: String
) : SearchItemState(id, "Drink")