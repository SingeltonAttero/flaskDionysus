package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 10.04.2020
 * @author YWeber */

sealed class DrinkDayItemState
data class AboutDrinkDayItem(val description: String) : DrinkDayItemState()
data class ListComponentDetailedItem(val components: List<DetailedComponentItemState>) : DrinkDayItemState()