package ru.yweber.flaskdionysus.model.entity

/**
 * Created on 20.04.2020
 * @author YWeber */

data class DrinkDayEntity(
    val id: Int,
    val preview: PreviewDrinkDayEntity,
    val detailed: DetailedDrinkDayEntity,
    val nameDrink: String,
    val previewIconPath: String,
    val isIba: Boolean,
    val isHot: Boolean,
    val isPuff: Boolean
)

data class PreviewDrinkDayEntity(
    val rating: Int,
    val tried: String,
    val fortress: String,
    val complication: String
)

data class DetailedDrinkDayEntity(
    val instruments: List<InstrumentEntity>,
    val ingredients: List<IngredientEntity>,
    val recipe: String,
    val description: String
)