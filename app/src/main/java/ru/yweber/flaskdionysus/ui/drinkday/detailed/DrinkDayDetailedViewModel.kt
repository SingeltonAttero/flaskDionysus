package ru.yweber.flaskdionysus.ui.drinkday.detailed

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.*
import ru.yweber.flaskdionysus.ui.drinkday.detailed.state.DrinkDayDetailedState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayDetailedViewModel : BaseViewModel<DrinkDayDetailedState>() {
    override val defaultState: DrinkDayDetailedState
        get() = DrinkDayDetailedState("", listOf())

    init {
        val listFormula = listOf(
            FormulaComponentItem("Текила", "40 мл"),
            FormulaComponentItem("Сок апельсиновый", "100 мл"),
            FormulaComponentItem("Гренадин", "20 мл"),
            FormulaComponentItem("Текила бум", "32 мл"),
            FormulaComponentItem("Сок апельсиновый добрый", "100 мл"),
            FormulaComponentItem("Гренадин синий", "20 мл"),
            DescriptionComponentItem("Налейте текилу и апельсиновй сок в бокал, перемешайте, затем добавьте гренадин")
        )
        val componentFormula = ListComponentDetailedItem(listFormula)
        val tools = listOf<DetailedComponentItemState>(
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Бокал “Мартини”"),
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Бокал “Мартини”  2"),
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Соломинка"),
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Мозг"),
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Зуб вампира"),
            ToolComponentItem("https://img1.wbstatic.net/big/new/8740000/8741441-1.jpg", "Печать короля")
        )
        val componentTools = ListComponentDetailedItem(tools)

        action.value = currentState.copy(
            drinkName = "Текила Санрайз",
            pageItem = listOf(
                AboutDrinkDayItem("Фраппе с арахисовой пастой. Измельчите в пюре 1/4 ст. арахисовой пасты с 1 ст. молока. Налейте в стакан немного шоколадного сиропа, сверху добавьте молоко с пастой. Медленно влейте газированную воду и размешайте. Источник: https://grandkulinar.ru/7418-50-holodnyh-napitkov.html Гранд Кулинар"),
                componentFormula,
                componentTools
            )
        )
    }
}