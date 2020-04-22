package ru.yweber.flaskdionysus.ui.drinkday.detailed

import kotlinx.coroutines.flow.collect
import ru.terrakok.cicerone.Router
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.AboutDrinkDayItem
import ru.yweber.flaskdionysus.core.adapter.state.FormulaComponentItem
import ru.yweber.flaskdionysus.core.adapter.state.ListComponentDetailedItem
import ru.yweber.flaskdionysus.core.adapter.state.ToolComponentItem
import ru.yweber.flaskdionysus.di.DrinkDayNestedRouter
import ru.yweber.flaskdionysus.model.interactor.DrinkDayUseCase
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.drinkday.detailed.state.DrinkDayDetailedState
import toothpick.InjectConstructor

/**
 * Created on 07.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDayDetailedViewModel(
    private val useCase: DrinkDayUseCase,
    @DrinkDayNestedRouter private val nestedRouter: Router
) : BaseViewModel<DrinkDayDetailedState>() {
    override val defaultState: DrinkDayDetailedState
        get() = DrinkDayDetailedState("", "", listOf(), false)

    init {
        launch {
            useCase.getDrinkDay()
                .collect { entity ->
                    val listFormula = entity.detailed.ingredients.map {
                        FormulaComponentItem(it.id, it.name, it.volume)
                    }
                    val listTool = entity.detailed.instruments.map {
                        ToolComponentItem(it.id, it.iconPath, it.name)
                    }

                    action.value = currentState.copy(
                        drinkName = entity.nameDrink,
                        previewPath = createPreviewPath(entity.previewIconPath),
                        pageItem = listOf(
                            AboutDrinkDayItem(entity.detailed.recipe),
                            ListComponentDetailedItem(listFormula),
                            ListComponentDetailedItem(listTool)
                        )
                    )
                }
        }
    }

    private fun createPreviewPath(previewPath: String): String {
        return if (previewPath.isEmpty()) {
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Manhattan_Cocktail2.jpg/500px-Manhattan_Cocktail2.jpg" // TODO Delete
        } else previewPath
    }


    fun endSharedAnimate() {
        action.value = currentState.copy(endShared = true)
    }

    fun navigateToPreview() {
        nestedRouter.backTo(Screens.DrinkDayPreviewScreen)
    }
}