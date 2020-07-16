package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 10.04.2020
 * @author YWeber */

sealed class DrinkDayItemState
data class AboutDrinkComponentItem(val description: String) : DrinkDayItemState()
data class ListComponentDetailedItem(val components: List<DetailedComponentItemState>) : DrinkDayItemState()
data class MainComponentDetailedItem(
    val tried: String,
    val fortress: String,
    val complication: String,
    val isFire: Boolean,
    val isPuff: Boolean,
    val isIba: Boolean
) : DrinkDayItemState()