package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 10.04.2020
 * @author YWeber */

sealed class DrinkDayItemState
data class AboutDrinkDayItem(val description: String) : DrinkDayItemState()
data class FormulaDrinkDayItem(val components: List<Unit>, val cookingDescription: String) : DrinkDayItemState()
data class ToolsDrinkDayItem(val tools: List<Unit>) : DrinkDayItemState()