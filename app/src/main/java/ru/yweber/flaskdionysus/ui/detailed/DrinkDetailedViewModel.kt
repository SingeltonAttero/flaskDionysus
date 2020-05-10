package ru.yweber.flaskdionysus.ui.detailed

import kotlinx.coroutines.flow.collect
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseViewModel
import ru.yweber.flaskdionysus.core.adapter.state.*
import ru.yweber.flaskdionysus.core.navigation.GlobalRouter
import ru.yweber.flaskdionysus.di.utils.PrimitiveWrapper
import ru.yweber.flaskdionysus.model.entity.DrinkDetailedEntity
import ru.yweber.flaskdionysus.model.interactor.DrinkDetailedUseCase
import ru.yweber.flaskdionysus.system.ResourceManager
import ru.yweber.flaskdionysus.ui.Screens
import ru.yweber.flaskdionysus.ui.detailed.state.DrinkDetailedState
import toothpick.InjectConstructor

/**
 * Created on 29.04.2020
 * @author YWeber */

@InjectConstructor
class DrinkDetailedViewModel(
    private val globalRouter: GlobalRouter,
    private val drinkId: PrimitiveWrapper<Int>,
    private val useCase: DrinkDetailedUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel<DrinkDetailedState>() {

    override val defaultState: DrinkDetailedState
        get() = DrinkDetailedState.defaultInstance()

    init {
        launch {
            useCase.getDrinkDetailed(drinkId.value)
                .collect {
                    action.value = currentState.copy(
                        title = it.name,
                        imagePath = it.imagePath,
                        titleEn = it.nameEn,
                        rating = it.rating,
                        listPage = listOf(
                            createMainComponent(it),
                            createAboutComponent(it),
                            createIngredientComponent(it),
                            createInstrumentComponent(it)
                        )
                    )
                }
        }
    }

    private fun createMainComponent(entity: DrinkDetailedEntity): MainComponentDetailedItem {
        return MainComponentDetailedItem(
            resourceManager.getString(R.string.tried_prefix, entity.tried),
            resourceManager.getString(R.string.alcohol_strength_prefix, entity.fortress),
            resourceManager.getString(R.string.cooking_level_prefix, entity.complication),
            entity.isFire,
            entity.isPuff,
            entity.isIba
        )
    }

    private fun createAboutComponent(entity: DrinkDetailedEntity): AboutDrinkComponentItem {
        return AboutDrinkComponentItem(entity.description)
    }

    private fun createIngredientComponent(entity: DrinkDetailedEntity): ListComponentDetailedItem {
        return ListComponentDetailedItem(entity.ingredients.map {
            FormulaComponentItem(it.id, it.name, it.volume)
        })
    }

    private fun createInstrumentComponent(entity: DrinkDetailedEntity): ListComponentDetailedItem {
        return ListComponentDetailedItem(entity.instruments.map {
            ToolComponentItem(it.id, it.iconPath, it.name)
        })
    }


    fun backTo() {
        globalRouter.backTo(Screens.HomeListDrinkScreen)
    }
}