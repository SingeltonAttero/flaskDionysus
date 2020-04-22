package ru.yweber.flaskdionysus.model.entity

/**
 * Created on 23.04.2020
 * @author YWeber */

data class FilterEntity(
    val id: Int,
    val name: String,
    val type: Type
) {

    enum class Type {
        INGREDIENT, OTHER, VOLUMES, FORTRESS_LEVELS, COMPLICATION_LEVELS
    }
}