package ru.yweber.flaskdionysus.model.entity

/**
 * Created on 07.05.2020
 * @author YWeber */

data class DrinkDetailedEntity(
    val id: Int,
    val name: String,
    val nameEn: String,
    val rating: Int,
    val imagePath: String,
    val tried: String,
    val fortress: String,
    val complication: String,
    val isFire: Boolean,
    val isPuff: Boolean,
    val isIba: Boolean,
    val description: String,
    val instruments: List<InstrumentEntity>,
    val ingredients: List<IngredientEntity>
)