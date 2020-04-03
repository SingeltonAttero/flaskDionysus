package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 01.04.2020
 * @author YWeber */

sealed class DrinkItem

data class DrinkCardItem(
    val drawableRes: Int,
    val nameDrink: String,
    val cookingLevel: String,
    val alcohol: String,
    val ingredients: String
) : DrinkItem()