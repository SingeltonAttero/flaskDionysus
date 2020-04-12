package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 11.04.2020
 * @author YWeber */

sealed class DetailedComponentItemState
data class ToolComponentItem(
    val toolIcon: String,
    val toolName: String
) : DetailedComponentItemState()

data class FormulaComponentItem(
    val ingredientsName: String,
    val volume: String
) : DetailedComponentItemState()

data class DescriptionComponentItem(
    val description: String
) : DetailedComponentItemState()