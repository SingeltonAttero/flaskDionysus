package ru.yweber.flaskdionysus.ui.detailed

import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.*
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.detailed.state.DrinkDetailedState
import toothpick.InjectConstructor

/**
 * Created on 29.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDetailedViewModel(private val globalRouter: GlobalRouter) : BaseViewModel<DrinkDetailedState>() {

    override val defaultState: DrinkDetailedState
        get() = DrinkDetailedState(listOf(), "", "", 5, "")

    init {
        action.value = currentState.copy(
            listPage = listOf(
                MainComponentDetailedItem(""),
                AboutDrinkComponentItem(
                    "Apparently we had reached a great height in the atmosphere, for the Apparently we had reached a great height in the atmosphere, for the \n" +

                            "Apparently we had reached a great height in the atmosphere, for the Apparently we had reached a great height in the atmosphere, for the"
                ),
                ListComponentDetailedItem(
                    listOf(
                        FormulaComponentItem(1, "Текила", "40 мл"),
                        FormulaComponentItem(2, "Сок апельсиновый", "40 мл"),
                        FormulaComponentItem(3, "Гренадин 5", "40 мл"),
                        FormulaComponentItem(4, "Гренадин 3", "40 мл"),
                        FormulaComponentItem(3, "Гренадин 3", "40 мл")
                    )
                ),
                ListComponentDetailedItem(
                    listOf(
                        ToolComponentItem(1, "", "Бокал “Мартини”"),
                        ToolComponentItem(2, "", "Соломинка"),
                        ToolComponentItem(3, "", "Труба"),
                        ToolComponentItem(4, "", "Стакан"),
                        ToolComponentItem(65, "", "Лес"),
                        ToolComponentItem(7, "", "Озеро"),
                        ToolComponentItem(8, "", "Остров")
                    )
                )
            ),
            title = "Текила Санрайз",
            titleEn = "Tequila sunrise",
            rating = 3,
            imagePath = "https://media.merixstudio.com/uploads/svg-logo-text-550x526.png"
        )
    }

    fun backTo() {
        globalRouter.backTo(Screens.HomeListDrinkScreen)
    }
}