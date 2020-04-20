package ru.yweber.flaskdionysus.core.adapter.state

/**
 * Created on 11.04.2020
 * @author YWeber */

sealed class DetailedComponentItemState(open val id: Int)
data class ToolComponentItem(
    override val id: Int,
    val toolIcon: String,
    val toolName: String
) : DetailedComponentItemState(id)

data class FormulaComponentItem(
    override val id: Int,
    val ingredientsName: String,
    val volume: String
) : DetailedComponentItemState(id)

data class DescriptionComponentItem(
    val description: String
) : DetailedComponentItemState(description.hashCode())