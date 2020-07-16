package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 01.04.2020
 * @author YWeber */

sealed class DrinkItem

data class DrinkCardItem(
    val id: Int,
    val iconPath: String,
    val nameDrink: String,
    val cookingLevel: String,
    val ingredients: String,
    val rating: Int,
    val flacky: Boolean,
    val fire: Boolean,
    val iba: Boolean
) : DrinkItem()
