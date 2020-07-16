package ru.yweber.flaskdionysus.model.entity

/**
 * Created on 15.05.2020
 * @author YWeber */

data class SearchEntity(
    val id: Int,
    val type: Type,
    val name: String
) {
    enum class Type {
        COCKTAIL,
        INGREDIENT,
        INSTRUMENT
    }
}