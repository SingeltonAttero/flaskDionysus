package ru.yweber.flaskdionysus.model.entity

import ru.yweber.flaskdionysus.system.error.ErrorStatus

/**
 * Created on 14.04.2020
 * @author YWeber */

sealed class DrinksEntity {
    data class Result(val list: List<DrinkEntity>) : DrinksEntity()
    data class Error(val error: ErrorStatus) : DrinksEntity()
}

data class DrinkEntity(
    val id: Int,
    val drinkName: String,
    val icon: String,
    val drinkRating: Int,
    val flacky: Boolean,
    val fire: Boolean,
    val iba: Boolean,
    val properties: String,
    val ingredients: String
)