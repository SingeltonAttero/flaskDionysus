package ru.yweber.flaskdionysus.ui.detailed.state

import ru.yweber.flaskdionysus.core.adapter.state.DrinkDayItemState

/**
 * Created on 29.04.2020
 * @author YWeber */

data class DrinkDetailedState(
    val listPage: List<DrinkDayItemState>,
    val title: String,
    val titleEn: String,
    val rating: Int,
    val imagePath: String
)